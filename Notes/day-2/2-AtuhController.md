# AuthController - Explicação completa

Este controller é responsável pelo **login** de usuários e pela **geração de tokens JWT** caso as credenciais estejam corretas.

---

## Código

```java
package com.abner.financialtracker.controller;

import com.abner.financialtracker.dto.LoginRequest;
import com.abner.financialtracker.model.User;
import com.abner.financialtracker.repository.UserRepository;
import com.abner.financialtracker.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = new JwtUtil(); // Usa JwtUtil diretamente aqui
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElse(null);

        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas"));
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(Map.of(
            "message", "Login realizado com sucesso!",
            "token", token
        ));
    }
}
```

---

## Explicação Detalhada

### 1. Anotações

- `@RestController`: Indica que a classe lida com requisições REST e que os métodos retornarão objetos JSON.
- `@RequestMapping("/auth")`: Define que todos os endpoints da classe começarão com `/auth`.

---

### 2. Dependências Injetadas

- `UserRepository`: usado para buscar o usuário no banco de dados pelo `username`.
- `JwtUtil`: classe utilitária que gera e valida tokens JWT. Instanciada diretamente, mas futuramente pode ser injetada com `@Component`.

---

### 3. Método `login`

#### Endpoint: `POST /auth/login`

Este método recebe um `LoginRequest` com `username` e `password`, valida as credenciais e, se estiver tudo correto, gera um token JWT.

##### Etapas do processo:

- `@RequestBody LoginRequest loginRequest`: o Spring converte automaticamente o JSON recebido em um objeto Java com os campos esperados.

- `userRepository.findByUsername(...)`: busca no banco de dados o usuário com o `username` informado.

- `if (user == null || !user.getPassword().equals(...))`: verifica se o usuário existe e se a senha confere. **Nota:** neste ponto a senha está em texto puro, sem criptografia.

- `jwtUtil.generateToken(...)`: gera um JWT com o `username` como subject.

- `return ResponseEntity.ok(...)`: retorna um status 200 com uma mensagem de sucesso e o token.

- Se as credenciais forem inválidas, retorna status 401 com erro.

---

## Exemplo de Requisição (Postman)

**Endpoint:** `POST http://localhost:8080/auth/login`

**Body (raw JSON):**

```json
{
  "username": "meuusuario",
  "password": "minhasenha"
}
```

**Resposta (exemplo):**

```json
{
  "message": "Login realizado com sucesso!",
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

---

## Considerações Futuras

- **Segurança**: atualmente as senhas não estão sendo armazenadas de forma segura (sem hash). Em breve será adicionado `BCryptPasswordEncoder`.

- **Validação de Token**: ainda não há proteção nos endpoints. Em breve, será implementado um filtro de segurança para interceptar requisições e verificar se o token JWT está presente e é válido.

---

## DTO Usado: `LoginRequest.java`

```java
package com.abner.financialtracker.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
```

Este DTO serve para mapear o corpo da requisição de login.

---
