package com.artesansoul.artesansoulV3.services;


import com.artesansoul.artesansoulV3.Repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado"));
    }
}
