package com.biblioteca.library_api.repository;

import com.biblioteca.library_api.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;






@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 🔍 Buscar usuario por username (Spring Data JPA crea la query automáticamente)
    Optional<User> findByUsername(String username);

     // 🔍 Verificar si existe un usuario con ese username
     boolean existsByUsername(String username);
}
