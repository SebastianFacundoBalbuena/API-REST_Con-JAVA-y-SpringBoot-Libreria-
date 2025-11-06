package com.biblioteca.library_api.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.biblioteca.library_api.service.CustomUserDetailsService;

import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;





public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException,IOException {

        // 🎯 SECCIÓN 1: EXTRAER Y VALIDAR HEADER TOKEN
        final String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;  
        
          // Verificar si existe header Authorization con formato Bearer

          if(authHeader != null && authHeader.startsWith("Bearer ")){
            // Extraer solo el token (remover "Bearer ")
            jwtToken = authHeader.substring(7); // Metodo eliminar caracteres

            try {
                 // Extraer username del token usando JwtUtil
                 username = jwtUtil.extractUsername(jwtToken); // Metodo que implementa la clase JwtUtil de extraccion

            } catch (Exception e) {
                 System.out.println("❌ Error extrayendo username del token: " + e.getMessage());
            }
          }else{
             System.out.println("ℹ️  No se encontró token JWT en el header");
          }


          // 🎯 SECCIÓN 2: VALIDAR TOKEN Y CREAR AUTENTICACIÓN
          if(username != null && SecurityContextHolder.getContext().getAuthentication() == null ){ //Hay una autenticacion iniciada ya? 

            
            try {
                // Cargar detalles del usuario desde UserDetailsService (memoria/BD)
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Validar token con JwtUtil y verifica que ambos user sean iguales
                if(jwtUtil.validateToken(jwtToken, userDetails.getUsername())){ 

                    // 🎯 SECCIÓN 3: CREAR AUTENTICACIÓN PARA SPRING

                    // Crear objeto Authentication que Spring Security entiende
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                     // Agregar detalles de la request (IP, sessionId, etc.)
                      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                      // 🎯 SECCIÓN 4: ESTABLECER AUTENTICACIÓN EN CONTEXTO
                      // ¡MAGIA! Establecer autenticación en el contexto de seguridad
                      SecurityContextHolder.getContext().setAuthentication(authToken);
                } else{
                    System.out.println("❌ Token inválido o expirado");
                }


            } catch (Exception e) {
                System.out.println("❌ Error durante la validación: " + e.getMessage());
            }

          }

           // 🎯 SECCIÓN 5: CONTINUAR CADENA DE FILTROS
            // Pasar la request al siguiente filtro en la cadena
        // (Con o sin autenticación establecida)
           filterChain.doFilter(request, response);

    }

}
