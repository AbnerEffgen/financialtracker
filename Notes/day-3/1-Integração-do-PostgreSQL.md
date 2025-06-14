# Integração do PostgreSQL com aplicação Spring Boot — Projeto FinancialTracker

## 1. Criação e configuração do banco de dados PostgreSQL

### Usando WSL:
- Instalamos o PostgreSQL no Ubuntu WSL.
- Criamos um novo usuário `abner` com:
  ```bash
  sudo -u postgres createuser --interactive
  sudo -u postgres psql
  ALTER USER abner WITH ENCRYPTED PASSWORD 'senha';
  ```
- Criamos o banco `financialtracker`:
  ```bash
  createdb -O abner financialtracker
  ```

### Usando pgAdmin:
- Instalamos e abrimos o pgAdmin no Windows.
- Criamos um novo banco `financialtracker` usando o usuário `postgres`.
- Garantimos que o usuário `abner` tinha permissões no schema `public`:
  ```sql
  GRANT ALL ON SCHEMA public TO abner;
  ```

## 2. Configuração da aplicação Spring Boot

### Dependência no `pom.xml`
Certificamo-nos de que a dependência do PostgreSQL estava no projeto:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.2</version>
</dependency>
```

### Configuração do `application.properties` ou `application.yml`
Usamos o seguinte bloco para conectar ao banco:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/financialtracker
spring.datasource.username=abner
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Observações:
- `ddl-auto=update`: permite que o Hibernate crie as tabelas automaticamente.
- Garantimos que a entidade `User` estava corretamente anotada com `@Entity` e `@Table(name = "users")`.

## 3. Problemas encontrados e soluções

- **Erro `relation "users" does not exist`**:
  - O Hibernate não conseguiu criar a tabela por falta de permissão no schema.
  - Solução: Garantimos as permissões com `GRANT ALL ON SCHEMA public TO abner;`.

- **Erro `permission denied for schema public`**:
  - Mesma causa. Resolvido com as permissões corretas no pgAdmin ou via SQL.

- **Dificuldades em excluir o banco via WSL**:
  - Banco estava atrelado ao usuário `postgres`. Usamos `DROP DATABASE` a partir do `psql` logado como `postgres`.

## 4. Teste final

- Ao rodar a aplicação com `./mvnw spring-boot:run`, a tabela `users` foi criada automaticamente.
- Confirmamos isso via pgAdmin e também com inserções via a própria aplicação.

---
Esse processo garantiu a integração estável do banco PostgreSQL com a aplicação Java Spring Boot do FinancialTracker.
