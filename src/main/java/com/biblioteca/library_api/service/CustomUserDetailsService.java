package com.biblioteca.library_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.biblioteca.library_api.model.User;

import com.biblioteca.library_api.repository.UserRepository;


// el "CONTRATO" de Spring Security
// A LA HORA DE BUSCAR EN LA DB SPRING NO ENTIENDE, SE MANEJA CON UserDetailsService
//Este metodo se encarga de buscar por spring en la DB para enviarleselo

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override 
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                // Buscar usuario en la base de datos
                Optional<User> user = userRepository.findByUsername(username);

                if(user.isPresent()){
                    return user.get();
                }else{
                    System.out.println("❌ Usuario no encontrado: " + username);
                    throw new UsernameNotFoundException("Usuario no encontrado");
                }


    }
}
