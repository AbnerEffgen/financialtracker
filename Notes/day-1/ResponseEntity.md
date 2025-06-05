# Melhorando as Respostas do `@PostMapping`

## Objetivo
Retornar mensagens mais completas e amigáveis ao fazer requisições POST, como a criação de um novo usuário, informando claramente:

- O status da operação
- Uma mensagem descritiva
- Os dados relevantes (por exemplo, o objeto criado)
- Tratamento de erros claros e padronizados

---

## Exemplo de método `createUser` melhorado

```java
@PostMapping
public ResponseEntity<?> createUser(@RequestBody User user) {
    try {
        User createdUser = userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                    "status", "success",
                    "message", "Usuário criado com sucesso!",
                    "data", createdUser
                ));
    } catch (RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                    "status", "error",
                    "message", "Erro ao criar usuário.",
                    "details", e.getMessage()
                ));
    }
}
```

---

## Explicação do que está acontecendo

### `@PostMapping`
Indica que esse método responde a requisições HTTP do tipo `POST`.

### `@RequestBody User user`
Diz ao Spring para converter o JSON vindo da requisição no corpo em um objeto `User`.

### `try/catch`
Usado para capturar exceções que podem ocorrer na lógica de salvar o usuário (por exemplo, se o username já existir).

### `userService.saveUser(user)`
Lógica da camada de serviço que valida e persiste o usuário no banco de dados.

---

### Em caso de sucesso:
```json
{
  "status": "success",
  "message": "Usuário criado com sucesso!",
  "data": {
    "id": 1,
    "username": "abner",
    "password": "********",
    "role": "USER"
  }
}
```

- Código HTTP retornado: `201 Created`

---

### Em caso de erro:
```json
{
  "status": "error",
  "message": "Erro ao criar usuário.",
  "details": "Usuário já existe"
}
```

- Código HTTP retornado: `400 Bad Request`

---

## Benefícios dessa estrutura
- **Melhor integração com o frontend** (espera estrutura padrão com status e dados).
- **Mais clareza em logs e debugging**.
- **Melhor documentação automática** (se você usar Swagger depois).

---

## Dica extra: usar DTOs
Para esconder campos sensíveis como `password`, o ideal é usar um DTO (Data Transfer Object) que só retorna os dados públicos.
Exemplo:
```java
record UserResponseDTO(Long id, String username, String role) {}
```
