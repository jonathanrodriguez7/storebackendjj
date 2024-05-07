package com.back.backend_jj.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.backend_jj.model.Produto;
import com.back.backend_jj.model.exception.ResourceNotFoundException;
import com.back.backend_jj.repository.ProdutoRepository;
import com.back.backend_jj.shared.ProdutoDTO;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> obterTodos(){
        // Retorna uma lista de produto model.
        List<Produto> produtos = produtoRepository.findAll();
        //expressao lambida utilizando map com stream ->
        return produtos.stream()
        .map(produto -> new ModelMapper().map(produto, ProdutoDTO.class))
        .collect(Collectors.toList());
    }
/**
 * Metodo que retorna o produto encontrado pelo seu Id.
 * @param id do produto que sera localizado find.
 * @return Retorna um produto caso tenha encontrado.
 */
public Optional<ProdutoDTO> obterPorId(Integer id){
    // Obtendo optional de produto pelo Id.
    Optional<Produto> produto = produtoRepository.findById(id);
    
    // se nao encontrar da o exception
    if(produto.isEmpty()){
        throw new ResourceNotFoundException("Produto com id:"+ id + "Nao encontrado");
    }
// Convertendo o meu optional em produtoDTO
    ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);
// criado e retornando um optional de um produtoDTO.    
    return Optional.of(dto);
}
 /**
     * Metodo para adicionar produto nalista.
     * @param produto que sera adicionado.
     * @return Retorna o produto que foi adicionado na lista. 
     */
    public ProdutoDTO adicionar(ProdutoDTO produtoDto){
        ///poderia ter regra de negocio para validar produto
        produtoDto.setId(null);
        //criar um objeto de mapeamento
        ModelMapper mapper = ModelMapper();

        //Converter produto DTO  em um produto.
        Produto produto= mapper.map(produtoDto, Produto.class);
        
        //Salvar o produto do banco
        produto = produtoRepository.save(produto);

        produtoDto.setId(produto.getId());
        
        // Retornar produtoDTO atualizado.
        return produtoDto;
    }
 private ModelMapper ModelMapper() {
    throw new UnsupportedOperationException("Unimplemented method 'ModelMapper'");
}
/**
     * Metodo que deleta o produto void vazio. vai deletar por
     * @param id
     */

     public void deletar(Integer id){
        //Verificar se o produto existe.

        Optional<Produto> produto = produtoRepository.findById(id);

        // se nao existir lanca uma exception
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Nao foi possivel deletar o produto com o id:"+ id +"- Produto nao existe");
            
        }
        // deleta o produto por id
        produtoRepository.deleteById(id);
    }
/**
 * Metodo para atualizar produto
 * @param produto que sera atualizado
 * @param id do produto
 * @return retorna o produto apos atualizar
 */
public ProdutoDTO atualizar(Integer id,ProdutoDTO produtoDto){
    // Passar o id para o produtoDto.

   produtoDto.setId(id);
    
    // Criar um objeto de mapeamento.

    ModelMapper mapper = new ModelMapper();

    // Converter o DTO em um produto.
    Produto produto = mapper.map(produtoDto, Produto.class);

    //Atualizar o produto no banco de dados.
    produtoRepository.save(produto);

    //Retornar o produto atualizado.
    return produtoDto;

    }
}