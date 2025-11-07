package com.biblioteca.library_api.controller;

import java.util.HashMap;
import java.util.Map;
import com.biblioteca.library_api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.biblioteca.library_api.config.JwtUtil;
import com.biblioteca.library_api.dto.LoginDTO;
import com.biblioteca.library_api.dto.RegisterDTO;
import com.biblioteca.library_api.repository.UserRepository;

import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.PostMapping;







@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;



    // REGISTRO DE USARIOS
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO user){

        try {
           // 🔍 PASO 1: VERIFICAR SI EL USUARIO YA EXISTE
            if( userRepository.existsByUsername(user.getUsername())){
                
                Map<String, String> error = new HashMap<>();
                error.put("mensaje", "El usuario ya existe");
                error.put("mensaje", "Elige otro nombre para el registro");

                return ResponseEntity.badRequest().body(error);
            }

            // 🔍 PASO 2: Si no existe, procedemos a encriptar su contraseña
            String passwordEncriptada = passwordEncoder.encode(user.getPassword());

            // 👤 PASO 3: CREAR NUEVO USUARIO Y GUARDARLO

            User newUser = new User(user.getUsername(),passwordEncriptada, "ROLE_USER");

            User userSave = userRepository.save(newUser);

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Usuario creado con exito!");
            response.put("username", userSave.getUsername());
            response.put("role", userSave.getRol());
            response.put("id", userSave.getId().toString());

            return ResponseEntity.ok(response);


        } catch (Exception e) {
            
            Map<String, String> responseError = new HashMap<>();
            responseError.put("mensaje", "No se ha podido crear al usuario");
            responseError.put("username", e.getMessage().toString());

            return ResponseEntity.status(500).body(responseError);
        }
    }

    




    // LOGIN DE USARIOS
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
