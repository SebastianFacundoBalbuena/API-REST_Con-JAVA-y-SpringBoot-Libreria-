package com.biblioteca.library_api.config;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;




@TestConfiguration
@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain testFilterChains(HttpSecurity http) throws Exception{
       http.csrf(csrf -> csrf.disable())
       .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

       return http.build();
    }

}
