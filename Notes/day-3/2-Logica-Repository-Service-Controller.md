# 💡 Como funciona o fluxo Repository → Service → Controller no Spring Boot

Neste guia, explicamos a estrutura em camadas usada em projetos Spring Boot, com exemplos do seu projeto **FinancialTracker**, focando na entidade `Transaction`.

---

## 🧩 Repository – Acesso ao Banco de Dados

A camada `Repository` lida diretamente com o banco de dados. Ao estender `JpaRepository`, o Spring já fornece métodos como `save`, `deleteById`, `findAll`, etc. Você também pode criar métodos personalizados com base em nomes.

### Exemplo usado:

```java
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
```

**Detalhes:**
- `findByUser`: busca todas as transações de um usuário.
- `findByUserAndDateBetween`: busca transações daquele usuário em um intervalo de datas.

---

## 💼 Service – Regras de Negócio

A camada `Service` faz a ponte entre a API (Controller) e o banco (Repository). Aqui também colocamos qualquer regra de negócio que quiser aplicar antes de salvar ou buscar algo.

### Exemplo:

```java
@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllByUser(User user) {
        return repository.findByUser(user);
    }

    public List<Transaction> getByUserAndDataRange(User user, LocalDate startDate, LocalDate endDate) {
        return repository.findByUserAndDateBetween(user, startDate, endDate);
    }

    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
```

---

## 🌐 Controller – Endpoints da API

A camada `Controller` é responsável por expor os endpoints HTTP. Ela chama os métodos da camada `Service` e retorna os dados para o frontend ou Postman.

### Exemplo:

```java
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;
    private final UserRepository userRepository;

    public TransactionController(TransactionService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Transaction> getAll(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                                  .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return service.getAllByUser(user);
    }
}
```

**Sobre o `Principal`:**
- O objeto `Principal` representa o usuário autenticado (via token JWT).
- `principal.getName()` retorna o `username`.
- Esse `username` é usado para buscar o `User` no banco com `userRepository.findByUsername(...)`.

---

## 🔁 Fluxo Resumido da Requisição

1. O Frontend/Postman faz uma requisição para `/transactions`.
2. O Spring usa o `token JWT` para identificar o usuário autenticado (via `Principal`).
3. O `Controller` chama o `Service`, passando o `User`.
4. O `Service` chama o `Repository`, que executa a consulta no banco.
5. A resposta percorre o caminho inverso e retorna os dados para o cliente.

---

## ✅ Conclusão

- A camada `Repository` fala com o banco.
- A `Service` aplica lógica de negócio.
- A `Controller` expõe os endpoints.
- O Spring Data JPA entende métodos como `findByUser` e cria as queries automaticamente.
- O `Principal` representa o usuário autenticado no contexto da requisição atual.

Essa estrutura modular deixa seu código limpo, organizado e fácil de manter.

```java
// Exemplo de endpoint GET com segurança:
@GetMapping
public List<Transaction> getAll(Principal principal) {
    User user = userRepository.findByUsername(principal.getName()).orElseThrow();
    return service.getAllByUser(user);
}
```
