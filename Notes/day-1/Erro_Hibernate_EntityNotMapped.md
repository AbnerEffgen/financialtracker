# Erro: Entity not mapped - Hibernate e nomes reservados

## Problema encontrado
Durante a execução do método `findByUsername(...)` no `UserService`, ocorreu uma exceção indicando que a entidade `User` não estava mapeada corretamente:

```
java.lang.IllegalArgumentException: org.hibernate.hql.internal.ast.QuerySyntaxException: User is not mapped
```

Esse erro surgiu apesar de a classe `User` estar anotada com `@Entity`, `@Id`, `@GeneratedValue` etc.

### Causa provável
A palavra `User` é **reservada** em alguns bancos de dados relacionais (como PostgreSQL). Como o Hibernate, por padrão, usa o nome da classe como nome da tabela (`user`), a query JPQL gerada pode conflitar com palavras reservadas do banco.

## Solução aplicada
Explicitamos o nome da entidade e da tabela manualmente:

```java
@Entity(name = "UserEntity")
@Table(name = "users")
public class User {
    // campos...
}
```

### Explicações:
- `@Entity(name = "UserEntity")`: define o nome usado em queries JPQL (ex: `SELECT u FROM UserEntity u`);
- `@Table(name = "users")`: define o nome real da tabela no banco (evita conflito com `user`, palavra reservada).

## Resultado
Após aplicar as mudanças, o erro foi resolvido e as chamadas ao banco via `UserRepository.findByUsername(...)` passaram a funcionar corretamente.

## Aprendizado
Sempre que for usar nomes comuns como `User`, `Order`, `Group`, etc., é **altamente recomendável** explicitar os nomes de entidade e tabela para evitar conflitos com palavras reservadas.

