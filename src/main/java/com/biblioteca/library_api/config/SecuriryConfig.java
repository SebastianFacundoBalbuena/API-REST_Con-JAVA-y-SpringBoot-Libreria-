package com.biblioteca.library_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;




@Configuration
@EnableWebSecurity
public class SecuriryConfig {


    //Encripta una contraseña
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Verifica los datos del login con los de la DB automaticamente
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{

        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
        http 
         .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(autho -> autho 
        .requestMatchers("/api/books/**").permitAll()  //Todos los libros son publicos
        .requestMatchers("api/auth/**").permitAll()
          .anyRequest().authenticated() //Cualquier otra ruta requiere login
                   
          )

        .formLogin(form -> form.disable()) //Luego del login acceso permitido
        .httpBasic(basic -> basic.disable());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Creamos usuario fijo
        UserDetails user = User.builder()
        .username("Sebastian")
        .password(passwordEncoder().encode("Tecnico.02"))
        .roles("USER")
        .build();

        return new InMemoryUserDetailsManager(user);
    }

}
