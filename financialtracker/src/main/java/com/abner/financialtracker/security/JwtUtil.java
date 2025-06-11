package com.abner.financialtracker.security;

// Classe principal da biblioteca JJWT para criar, assinar, compactar, analisar e validar tokens JWT.
import io.jsonwebtoken.Jwts;

// Enum que define os algoritmos de assinatura (como HS256, RS256) usados para assinar o token JWT.
import io.jsonwebtoken.SignatureAlgorithm;

// Classe utilitária para gerar chaves seguras (por exemplo, para HMAC-SHA) a partir de bytes ou aleatoriamente.
import io.jsonwebtoken.security.Keys;

// Classe para decodificar strings Base64 (como sua chave secreta codificada) em arrays de bytes.
import io.jsonwebtoken.io.Decoders;

// Representa as informações (claims) contidas no corpo de um token JWT (como username, expiração, etc.).
import io.jsonwebtoken.Claims;

// Classe da biblioteca Java padrão usada para representar uma chave criptográfica (usada para assinar/verificar o token).
import java.security.Key;

// Representa uma data e hora no formato padrão da API Java (usado para definir emissão e expiração do token).
import java.util.Date;

import java.util.Date;

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
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public static String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public static boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
