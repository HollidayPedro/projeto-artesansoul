package com.artesansoul.artesansoulV3.Repositorio;



import com.artesansoul.artesansoulV3.Models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente,Long > {
    Optional<Cliente> findByEmail(String email);

    boolean existsByEmail(String email);

}
