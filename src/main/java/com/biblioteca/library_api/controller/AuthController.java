package com.biblioteca.library_api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.biblioteca.library_api.config.JwtUtil;
import com.biblioteca.library_api.dto.LoginDTO;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.PostMapping;







@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){


        try {
            
                      // 🎯 PASO 1: VERIFICAR CREDENCIALES CON SPRING SECURITY
          Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
          );

          // 🎯 PASO 2: SI LLEGA AQUÍ, LAS CREDENCIALES SON VÁLIDAS
          String username = authentication.getName();

          // 🎯 PASO 3: GENERAR TOKEN JWT
          String token = jwtUtil.generateToken(username);

               // 🎯 PASO 4: PREPARAR RESPUESTA
               Map<String,String> response = new HashMap<>();
               response.put("token", token);
               response.put("username", username);
               response.put("mensaje", "Login exitoso");

               return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 🎯 PASO 5: SI LAS CREDENCIALES SON INCORRECTAS
            Map<String,String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Credenciales invalidas");
            errorResponse.put("mensaje", "Verifica tu user y pass");

            return ResponseEntity.status(404).body(errorResponse);
        }

    }
    
}
