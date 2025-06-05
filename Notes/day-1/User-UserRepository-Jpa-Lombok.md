# 🧠 User + UserRepository + JPA + Lombok

## 👤 Entidade User (JPA)

A classe `User` representa um usuário do sistema e é persistida como uma tabela no banco de dados via JPA.

### 📦 Imports essenciais
```java
import jakarta.persistence.*; // ou javax.persistence.* no Spring Boot 2.x
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
```

### 🛠 Anotações
- `@Entity`: transforma a classe em uma tabela
- `@Id`: define o campo como chave primária
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: auto incremento
- `@Column`: configurações extras para a coluna (ex: `nullable`, `unique`)
- `@Enumerated(EnumType.STRING)`: para campos enum
- `@Data`: gera getters, setters, equals, hashCode, toString
- `@NoArgsConstructor`: construtor vazio exigido pelo JPA
- `@AllArgsConstructor`: construtor com todos os campos

### ✅ Exemplo de campos
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false, unique = true)
private String username;

@Column(nullable = false)
private String password;

@Enumerated(EnumType.STRING)
private Role role;
```

> Obs: `Role` deve ser um enum separado, com valores como `USER`, `ADMIN`.

---

## 📁 Interface UserRepository

Crie no pacote `repository`. Ela estende `JpaRepository` e fornece métodos prontos para CRUD:

```java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

### 🔍 Explicação:
- `JpaRepository<User, Long>`: entidade `User`, tipo do ID é `Long`
- `findByUsername`: Spring gera o SQL automaticamente com base no nome do método

---

## 🔗 O que é JPA?

JPA (Java Persistence API) é uma **especificação** que permite mapear objetos Java para tabelas do banco de dados.

- Usa anotações como `@Entity`, `@Id`, etc.
- Não executa nada sozinho → o Spring Boot usa o **Hibernate** por trás

---

## 🔧 O que é Lombok?

Lombok é uma biblioteca que **gera código repetitivo automaticamente**.

### ⚡ Vantagens:
- Reduz o número de linhas
- Ajuda a manter o foco na lógica
- Melhora a clareza da entidade

### ✨ Anotações úteis:
- `@Getter`, `@Setter`: gera métodos de acesso
- `@Data`: inclui tudo de uma vez
- `@NoArgsConstructor`, `@AllArgsConstructor`: construtores
- `@Builder`: construção fluente de objetos (opcional)

---

## 🐞 Problemas e soluções comuns

- **Erro `javax.persistence does not exist`**  
  → adicionar dependência do JPA no `pom.xml`

- **Erro `Whitelabel Error Page` ao acessar `/`**  
  → nenhuma rota foi mapeada ainda (normal)

- **Campo `role` enum salvo como número**  
  → adicionar `@Enumerated(EnumType.STRING)` para salvar como texto

- **Maven não reconhece o Lombok**  
  → adicionar a dependência e instalar extensão no VS Code

---

## 📌 Checklist ao criar uma entidade

- [x] Criar classe com `@Entity`
- [x] Adicionar `@Id` e `@GeneratedValue`
- [x] Criar atributos com `@Column`
- [x] Adicionar `@NoArgsConstructor` (e `@AllArgsConstructor` se quiser)
- [x] Usar `@Data` ou `@Getter/@Setter`
- [x] Criar `Repository` com `JpaRepository`

---

Esse bloco cobre tudo que envolve a modelagem do `User` e o acesso com JPA e Lombok.
