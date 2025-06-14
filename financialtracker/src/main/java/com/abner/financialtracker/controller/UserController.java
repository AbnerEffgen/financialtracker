package com.abner.financialtracker.controller;

import com.abner.financialtracker.model.User;
import com.abner.financialtracker.service.UserService;

// Permite que o Spring injete automaticamente uma dependência (injeção de dependência via @Autowired).
import org.springframework.beans.factory.annotation.Autowired;

// Representa os códigos de status HTTP padronizados (como 200 OK, 404 Not Found, 500 Internal Server Error, etc.).
import org.springframework.http.HttpStatus;

// Representa uma interface mais genérica usada internamente pelo Spring para lidar com códigos HTTP (substitui Integer puro).
import org.springframework.http.HttpStatusCode;

// Permite retornar uma resposta HTTP personalizada, incluindo status, headers e corpo (ex: ResponseEntity.ok(obj)).
import org.springframework.http.ResponseEntity;

// Conjunto de anotações para construir controladores REST:
import org.springframework.web.bind.annotation.*;
// @RestController → Marca a classe como controlador REST.
// @RequestMapping → Mapeia requisições para caminhos específicos.
// @GetMapping, @PostMapping, @PutMapping, @DeleteMapping → Mapeiam métodos HTTP específicos para métodos Java.

// Importa a interface List, uma coleção ordenada de elementos (por exemplo, List<String>).
import java.util.List;

// Importa a interface Map, usada para armazenar pares chave-valor (por exemplo, Map<String, Object>).
import java.util.Map;

@RestController
@RequestMapping("/auth/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> creatUser(@RequestBody User user) {
        try {
            User createdUser = userService.saveUser(user);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Usuário criado com sucesso!",
                            "userID", createdUser.getId(),
                            "userName", createdUser.getUsername()));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
