package com.biblioteca.library_api.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;





@Entity
@Table(name = "Users")
public class User implements UserDetails {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY) //autoincremental
private Long id;

@NotBlank(message = "El username es obligatorio")
@Column(name = "username", nullable = false, length = 50, unique = true)
private String username;

@NotBlank(message = "La contraseña es obligatoria")
@Column(name = "password", nullable = false, length = 130)
private String password;

@NotBlank(message = "El rol es obligatorio")
@Column(name = "role", nullable = false)
private String role;

// CONSTRUCTOR
public User(){}


public User(@NotBlank(message = "El username es obligatorio") String username,
        @NotBlank(message = "La contraseña es obligatoria") String password,
        @NotBlank(message = "El rol es obligatorio") String rol) {
    this.username = username;
    this.password = password;
    this.role = rol;
}

//GETTERS Y SETTERS

public Long getId(){
    return id;
}

public String getUsername() {
    return username;
}


public void setUsername(String username) {
    this.username = username;
}


public String getPassword() {
    return password;
}


public void setPassword(String password) {
    this.password = password;
}


public String getRol() {
    return role;
}


public void setRol(String rol) {
    this.role = rol;
}



// METODOS OVERRIDE

// Convierte: "ROLE_USER" → Objeto que Spring Security lo entienda 
//Si role = "ROLE_ADMIN" → Puede acceder a endpoints con .hasRole("ADMIN")
@Override
public Collection<? extends GrantedAuthority> getAuthorities(){
    
    return List.of(new SimpleGrantedAuthority(role));
} 


// METODOS A LA HORA DE QUE EL ADMIN CREE O MODIFIQUE EL USUARIO
// BRINDA PERMISOS ESPECIALES DE ACCESO A ENDPOINTS
@Override
public boolean isAccountNonExpired() { return true; } // La cuenta NO está expirada

 @Override
    public boolean isAccountNonLocked() { return true; } // La cuenta NO está bloqueada

    @Override
    public boolean isCredentialsNonExpired() { return true; } // La contraseña NO está expirada  

    @Override
    public boolean isEnabled() { return true; } // La cuenta está ACTIVA

}
