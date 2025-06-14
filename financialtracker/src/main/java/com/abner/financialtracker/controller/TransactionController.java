package com.abner.financialtracker.controller;

// Importa a classe que representa o usuário autenticado (login)
import java.security.Principal;
// Importa a classe que representa uma data (sem horário)
import java.time.LocalDate;
// Importa a classe que representa listas
import java.util.List;

// Classe usada para retornar respostas HTTP com status e corpo
import org.springframework.http.ResponseEntity;
// Anotação usada para mapear requisições HTTP DELETE
import org.springframework.web.bind.annotation.DeleteMapping;
// Anotação usada para mapear requisições HTTP GET
import org.springframework.web.bind.annotation.GetMapping;
// Anotação usada para extrair variáveis de dentro da URL
import org.springframework.web.bind.annotation.PathVariable;
// Anotação usada para mapear requisições HTTP POST
import org.springframework.web.bind.annotation.PostMapping;
// Anotação usada para capturar o corpo da requisição (JSON -> objeto)
import org.springframework.web.bind.annotation.RequestBody;
// Define a URL base para os endpoints do controller
import org.springframework.web.bind.annotation.RequestMapping;
// Anotação usada para capturar parâmetros da URL (?param=valor)
import org.springframework.web.bind.annotation.RequestParam;
// Anotação que marca essa classe como um controller REST (retorna JSON)
import org.springframework.web.bind.annotation.RestController;

// Importa a classe de modelo que representa uma transação
import com.abner.financialtracker.model.Transaction;
// Importa a classe de modelo que representa o usuário
import com.abner.financialtracker.model.User;
// Serviço responsável pela lógica das transações
import com.abner.financialtracker.service.TransactionService;
// Serviço responsável pela lógica relacionada a usuários
import com.abner.financialtracker.service.UserService;

@RestController // Indica que essa classe é um controller REST (retorna JSON)
@RequestMapping("/api/transactions") // Define o caminho base para todas as rotas deste controller
public class TransactionController {

    // Injeção de dependência do serviço de transações
    private final TransactionService transactionService;
    // Injeção de dependência do serviço de usuários
    private final UserService userService;

    // Construtor para inicializar os serviços usados pelo controller
    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    // GET /api/transactions
    // Retorna todas as transações do usuário logado
    @GetMapping
    public ResponseEntity<List<Transaction>> getAll(Principal principal) {
        // Recupera o usuário com base no nome do usuário logado
        User user = userService.getByUsername(principal.getName());
        // Retorna todas as transações do usuário como resposta HTTP 200 (OK)
        return ResponseEntity.ok(transactionService.getAllByUser(user));
    }

    // GET /api/transactions/Range?start=yyyy-mm-dd&end=yyyy-mm-dd
    // Retorna transações entre duas datas informadas
    @GetMapping("/Range")
    public ResponseEntity<List<Transaction>> getByDateRange(
            // Parâmetro de data inicial no formato string
            @RequestParam String start,
            // Parâmetro de data final
            @RequestParam String end,
            // Representa o usuário logado
            Principal principal) {
        // Recupera o usuário com base no nome do usuário logado
        User user = userService.getByUsername(principal.getName());
        // Converte string para tipo LocalDate
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        // Retorna as transações no intervalo de datas
        return ResponseEntity.ok(transactionService.getByUserAndDataRange(user, startDate, endDate));
    }

    // POST /api/transactions
    // Cria uma nova transação para o usuário logado
    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction, Principal principal) {
        // Recupera o usuário com base no nome do usuário logado
        User user = userService.getByUsername(principal.getName());
        // Associa o usuário à transação
        transaction.setUser(user);
        // Salva a transação no banco de dados e retorna a transação criada
        return ResponseEntity.ok(transactionService.save(transaction));
    }

    // DELETE /api/transactions/{id}
    // Deleta uma transação com base no ID passado na URL
    @DeleteMapping("/{id}") // Corrigido para incluir o parâmetro {id}
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Remove a transação com o ID fornecido
        transactionService.delete(id);
        // Retorna resposta sem conteúdo (HTTP 204)
        return ResponseEntity.noContent().build();
    }

}
