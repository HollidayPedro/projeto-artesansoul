package com.artesansoul.artesansoulV3.Repositorio;


import com.artesansoul.artesansoulV3.Models.Cliente;
import com.artesansoul.artesansoulV3.Models.Produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProdutoRepository extends JpaRepository <Produto, Long> {

    List<Produto> findByCliente(Cliente cliente);

}
