# 🧠 UserService.md

## 📁 Arquivo: `UserService.java`

Essa classe gerencia a lógica de negócio relacionada ao `User`. Ela depende do `UserRepository`, que acessa o banco de dados.

---

## 🔧 Injeção via Construtor

```java
private final UserRepository userRepository;

public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
}
```

**Explicação:**
- `private final`: indica que `userRepository` é imutável e só pode ser setado no construtor
- `UserRepository` é injetado automaticamente pelo Spring (injeção de dependência)

---

## 🔍 Sobre `Optional<T>`

- `Optional<User>` representa um resultado que **pode ou não estar presente**
- Evita o uso de `null` e `NullPointerException`

**Exemplo:**
```java
Optional<User> user = userRepository.findByUsername("admin");
user.ifPresent(u -> System.out.println(u.getUsername()));
```

---

## ✅ Métodos implementados no serviço:

### 1. `saveUser`
```java
public User saveUser(User user) {
    Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
    if (existingUser.isPresent()) {
        throw new RuntimeException("Usuário já existe");
    }
    return userRepository.save(user);
}
```

**Explicação:** salva usuário se o `username` ainda não existe.

---

### 2. `getUserByUsername`
```java
public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
}
```

**Explicação:** retorna um usuário opcional com base no nome de usuário.

---

### 3. `getAllUsers`
```java
public List<User> getAllUsers() {
    return userRepository.findAll();
}
```

**Explicação:** retorna todos os usuários do banco (útil para admin ou debug).

---

### 4. `deleteUserById`
```java
public void deleteUserById(Long id) {
    if (!userRepository.existsById(id)) {
        throw new RuntimeException("ID não encontrado");
    }
    userRepository.deleteById(id);
}
```

**Explicação:** verifica se o ID existe antes de deletar. Evita erro silencioso.

---

## 📌 Observações importantes

- As exceções lançadas (`RuntimeException`) podem futuramente ser substituídas por exceções customizadas com HTTP codes.
- Todos os métodos usam `UserRepository`, que é uma interface que herda de `JpaRepository<User, Long>`.

---

## ✅ Conclusão até aqui

- Criamos o `UserService` com todos os métodos básicos
- Aprendemos sobre `final`, injeção via construtor e uso de `Optional`
- Estamos prontos para criar o `UserController` e expor os endpoints via HTTP
