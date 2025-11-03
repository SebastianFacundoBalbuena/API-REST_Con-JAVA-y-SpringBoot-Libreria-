package com.biblioteca.library_api.config;


import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;




@Component
public class JwtUtil {

    // Clave secreta para firmar los tokens / se genera automaticamente
    private final Key Secret_Key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    //Tiempo de expiracion 1hs
    private final Long Expiration_Time = 1000L * 60L * 60L;


    //Generar toquen 
    public String generateToken(String username){
        return Jwts.builder()
        .setSubject(username)  // Quién es el dueño del token
        .setIssuedAt(new Date()) // Cuándo se creó
        .setExpiration(new Date(System.currentTimeMillis() + Expiration_Time)) // Cuándo expira
        .signWith(Secret_Key) // Firma con la clave secreta
        .compact(); // Convierte a string
    }

    //METODO EXTRAER NOMBRE
    private Claims extractClaims(String token){
        return Jwts.parserBuilder()   // 👷 Preparamos el constructor
        .setSigningKey(Secret_Key)   // 🔑 Verificamos que la clave es la misma y no se ha modificado
        .build()                     //Verifica que no esté vencido (expiración)
        .parseClaimsJws(token)        // 🔍 Analizamos y validamos el token/ la informacion
        .getBody();                   // 📄 Extraemos la información del usuario
    }


    //Metodo verificar si expiro el token
    private boolean isTokenExpired(String token){
        return extractClaims(token) //Obtener el objeto Claims
        .getExpiration() //Extraer la fecha de expiración
        .before(new Date()); //Comparar con fecha actual
    }

    //Extraer el nombre del TOKEN
    public String extractUsername(String token){
        return  extractClaims(token).getSubject();
    }

    //Verificamos si el token es valido
    public boolean validateToken(String token, String username){
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }


}
