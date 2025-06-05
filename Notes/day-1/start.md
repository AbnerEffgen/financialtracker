# 📘 Aprendizados com Spring Boot no WSL

## 🚀 Setup e Estrutura

- Projeto criado com Spring Initializr: Java 17, Spring Boot 3.x, Maven
- Estrutura básica organizada por pacotes:
  ```
  com.abner.financialtracker
  ├── controller
  ├── model
  ├── repository
  ├── service
  ├── config
  ```
- No VS Code com WSL (Ubuntu) para evitar bugs de caminhos com acento e espaços

## 🧱 Banco de Dados

- Para desenvolvimento inicial, usei **H2 (em memória)**:
  - Requer dependência no `pom.xml`: `com.h2database:h2`
  - Configuração mínima no `application.properties`
  - Erro comum: `Cannot load driver class: org.h2.Driver` → resolvido adicionando a dependência

- Para produção/local real, PostgreSQL será usado:
  - Necessário configurar `spring.datasource.url`, `username`, `password`
  - PostgreSQL deve estar rodando no host (`localhost`) ou no Docker

## 🔐 Spring Security (Comportamento padrão)

- Ao adicionar Spring Security, o Spring gera:
  - Usuário `user`
  - Senha aleatória impressa no terminal
  - Protege **todas as rotas automaticamente**
- Para desativar temporariamente, criei `SecurityConfig.java` com:
  ```java
  http.csrf().disable().authorizeHttpRequests().anyRequest().permitAll();
  ```

## 🛠 Controle de versão

- `.gitignore` deve conter `/target`, `.vscode`, `.idea`, `*.log`, `.env`
- Se o repositório do GitHub já tiver commits (ex: README), usar:
  ```
  git pull origin main --allow-unrelated-histories
  ```
- Se o Git abrir o `vim` pedindo mensagem de commit:
  - Pressionar `i`, escrever ou manter
  - `Esc`, digitar `:wq`, pressionar `Enter`

## ⚙️ Terminal e comandos úteis

- `chmod +x mvnw`: necessário para executar o wrapper Maven
- `ls -a`: lista arquivos ocultos (ex: `.gitignore`)
- `pwd`: mostra o diretório atual
- `code .`: abre o VS Code no WSL (com extensão Remote WSL)

## 🐛 Problemas enfrentados

- Caminhos com `á`, `ç` e espaço no Windows quebram o Maven → resolvido com WSL
- Repositório Git inicializado no lugar errado → corrigido com `rm -rf .git`
- Spring Boot mostra página branca (`Whitelabel Error`) quando nenhuma rota é mapeada
- `404 Not Found` no `/` é esperado quando não há `@GetMapping("/")`

## ✅ Teste de funcionamento

- Primeiro controller criado com:
  ```java
  @RestController
  public class HelloController {
      @GetMapping("/hello")
      public String hello() {
          return "FinancialTracker rodando!";
      }
  }
  ```

---

Esses aprendizados servem como base sólida para iniciar o projeto e resolver os problemas comuns rapidamente. Com isso, a estrutura está pronta para seguir com autenticação, CRUDs e integração com PostgreSQL.
