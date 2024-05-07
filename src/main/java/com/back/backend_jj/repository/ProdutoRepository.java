package com.back.backend_jj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.back.backend_jj.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    
}  
