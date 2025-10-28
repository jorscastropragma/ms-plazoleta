package com.plazoleta.plazoleta.infraestructure.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${app.jwt.llave}")
    private String llave;

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(llave.getBytes());
    }

    public Boolean validarToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims obtenerClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String obtnerCorreo(String token) {
        return obtenerClaims(token).getSubject();
    }

    public String obtenerRole(String token) {
        return obtenerClaims(token).get("rol", String.class);
    }
}
