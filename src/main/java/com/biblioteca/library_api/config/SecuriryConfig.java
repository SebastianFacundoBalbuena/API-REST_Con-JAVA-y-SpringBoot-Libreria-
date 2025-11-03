package com.biblioteca.library_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;







@Configuration
@EnableWebSecurity
public class SecuriryConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
        http 
        .authorizeHttpRequests(autho -> autho 
        .requestMatchers("/api/books/**").permitAll() //Todos los libros son publicos
          .anyRequest().authenticated()  //Cualquier otra ruta requiere login
        )

        .formLogin(form -> form.defaultSuccessUrl("/api/books", true)); //Luego del login acceso permitido

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Creamos usuario fijo
        UserDetails user = User.builder()
        .username("Sebastian")
        .password("{noop}Tecnico.02")
        .roles("USER")
        .build();

        return new InMemoryUserDetailsManager(user);
    }

}
