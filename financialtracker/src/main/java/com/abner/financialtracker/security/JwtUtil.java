package com.abner.financialtracker.security;

// Classe principal da biblioteca JJWT, usada para construir, assinar, validar e analisar tokens JWT.
import io.jsonwebtoken.Jwts;

// Enum que representa os algoritmos de assinatura digital disponíveis, como HS256 (HMAC com SHA-256).
import io.jsonwebtoken.SignatureAlgorithm;

// Classe utilitária para gerar e validar chaves seguras usadas na assinatura de tokens.
import io.jsonwebtoken.security.Keys;

// Classe usada para decodificar uma string Base64 (ex: sua chave secreta codificada) em um array de bytes.
import io.jsonwebtoken.io.Decoders;

// Representa o conteúdo (claims) do token JWT, como subject, issuedAt, expiration, etc.
import io.jsonwebtoken.Claims;

// Anotação que marca a classe como um bean Spring de componente genérico, permitindo que ela seja detectada automaticamente.
import org.springframework.stereotype.Component;

// Representa uma chave criptográfica usada para assinar/verificar tokens (ex: HMAC-SHA256).
import java.security.Key;

// Classe da biblioteca padrão do Java usada para trabalhar com datas, útil para definir emissão e expiração do token.
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY_BASE64 = "YXJoaXNhaW5kZXZlbG9ASwdasdWGV2ZWxvcGVzYXJoaXNhaW9wZXJvZGV2ZWxvcGU=";
    private final long EXPIRATION_TIME = 86400000 * 7; // 7 dias

    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_BASE64);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
