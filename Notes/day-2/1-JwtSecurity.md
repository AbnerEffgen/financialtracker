# JwtSecurity.md

## Objetivo
Adicionar autenticação baseada em JWT (JSON Web Token) para proteger as rotas da aplicação `FinancialTracker`.

---

## 🔐 O que é JWT?

JWT é um padrão para autenticação onde o servidor **gera um token assinado** com informações do usuário. Esse token é enviado ao cliente, que o usa em requisições subsequentes como **prova de identidade**. O servidor valida o token, sem precisar manter estado de sessão (stateless).

---

## ❓ O que é uma chave HMAC-SHA?

HMAC-SHA (Hash-based Message Authentication Code with SHA algorithm) é um algoritmo criptográfico que combina um *hash* (SHA256, por exemplo) com uma chave secreta para garantir que os dados não foram alterados e que vêm de uma fonte confiável.

No contexto de JWT:

- A chave HMAC-SHA é usada para **assinar digitalmente** o token.
- Com essa assinatura, o servidor pode verificar se o token foi gerado por ele e que não foi alterado por terceiros.
- O algoritmo usado no exemplo (`HS256`) é HMAC com SHA-256.

A chave secreta é convertida a partir de uma string codificada em Base64:

```java
private static final String SECRET_KEY_BASE64 = "YXJ...";
byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_BASE64);
return Keys.hmacShaKeyFor(keyBytes);
```

---

## 🔐 O que é um token JWT?

Um **token JWT (JSON Web Token)** é uma string segura e compacta composta por três partes:

1. **Header** (Cabeçalho): Informa o algoritmo de assinatura e o tipo do token.
2. **Payload** (Corpo): Contém as informações do usuário (como `username`, `roles`, etc.).
3. **Signature** (Assinatura): Garante a integridade do token. É gerada com a chave secreta e o algoritmo (ex: HMAC-SHA256).

Formato geral:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c3VhcmlvIiwiaWF0IjoxNj...G5UIn0._Assinatura_
```

---

## 🛠️ Como o token JWT é criado?

1. **Você fornece um `username`** (ou outro dado identificador).
2. O método `generateToken`:
   - Cria o `Header` (com algoritmo HS256).sss
   - Define o `Payload` com `subject`, `issuedAt`, `expiration`.
   - Gera a `Signature` com `SECRET_KEY` e `HS256`.
3. O método `.compact()` gera a string final do token.

Exemplo no código:

```java
return Jwts.builder()
    .setSubject(username)
    .setIssuedAt(new Date())
    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 horas
    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
    .compact();
```

---

## 🔑 Classe `JwtUtil`

```java
package com.abner.financialtracker.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Esta é a chave secreta usada para assinar e verificar o token.
    // Todos os tokens da aplicação usam essa mesma chave.
    private static final String SECRET_KEY_BASE64 = "YXJoaXNhaW5kZXZlbG9ASwdasdWGV2ZWxvcGVzYXJoaXNhaW9wZXJvZGV2ZWxvcGU=";

    private Key getSignInKey() {
        // Decodifica a chave base64 para bytes
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_BASE64);
        return Keys.hmacShaKeyFor(keyBytes); // Cria uma chave do tipo HMAC-SHA válida
    }

    public String generateToken(String username) {
        // Gera um token JWT com subject = username, tempo de expiração de 24h, e assinado com a chave
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // agora
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // algoritmo de assinatura
                .compact();
    }

    public String extractUsername(String token) {
        // Extrai o nome de usuário (subject) contido no token
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // usa a mesma chave para validar o token
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        // Verifica se o token ainda é válido e se o username bate com o subject
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        // Verifica se a data de expiração já passou
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
```
---

## 📌 Observações importantes

- **A chave secreta está codificada em Base64** para ser compatível com os algoritmos de assinatura como HS256.
- Todos os tokens são **assinados com a mesma chave** — isso é o padrão em APIs JWT.
- O token JWT é **stateless**: o servidor não armazena sessões, ele apenas verifica se o token recebido é válido e confere as informações internas.
- O `JwtUtil`:
  - Gera um token com username e validade de 24 horas.
  - Extrai informações do token (username).
  - Verifica validade (expiração e correspondência com o usuário).

---

## 💡 Melhoria futura

Ao invés de hardcodar a `SECRET_KEY_BASE64`, é recomendável colocá-la no `application.properties`:

```properties
jwt.secret=YXJoaXNhaW5kZXZlbG9ASwdasdWGV2ZWxvcGVzYXJoaXNhaW9wZXJvZGV2ZWxvcGU=
```

E injetar ela na classe `JwtUtil` usando `@Value`.

---

## 🔁 O que precisa ser reiniciado?

Sempre que o código Java for alterado (como o `JwtUtil.java`), você deve rodar:

```bash
./mvnw clean install
```

Ou, se estiver com Spring Boot Devtools configurado, o servidor reinicia automaticamente. Em produção, reinicie o serviço Spring Boot.

---
