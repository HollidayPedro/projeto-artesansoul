package com.artesansoul.artesansoulV3.services;


import com.artesansoul.artesansoulV3.Models.Cliente;
import com.artesansoul.artesansoulV3.Repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void salvar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public boolean existsByEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }

    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email).orElse(null);
    }
}

