package com.biblioteca.library_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;





@Configuration
@EnableWebSecurity
public class SecuriryConfig {


    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }


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

          // 🎯 SECCIÓN: CONFIGURACIÓN DE AUTORIZACIÓN

           // 🔐 ENDPOINTS PROTEGIDOS (requieren token válido)
        .requestMatchers(HttpMethod.POST,"/api/books/**").authenticated()
        .requestMatchers(HttpMethod.PUT,"/api/books/**").authenticated()
        .requestMatchers(HttpMethod.DELETE,"/api/books/**").authenticated()
        
        // 🔓 ENDPOINTS PÚBLICOS (no requieren token)
        .requestMatchers("/api/auth/**").permitAll()    //Login publico
        .requestMatchers("/api/books/**").permitAll()   //Todos los libros son publicos


        // 🔓 SWAGGER - TODAS LAS RUTAS NECESARIAS

            .requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**", 
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/configuration/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/api-docs/**",           
                "/api-docs/swagger-config", 
                "/favicon.ico",
                "/error",
                "/actuator/**"
            ).permitAll()

          .anyRequest().authenticated() //Cualquier otra ruta requiere login
                   
          )

        .formLogin(form -> form.disable()) //Luego del login acceso permitido
        .httpBasic(basic -> basic.disable())

        // 🎯 SECCIÓN: AGREGAR FILTRO JWT A LA CADENA
        .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
