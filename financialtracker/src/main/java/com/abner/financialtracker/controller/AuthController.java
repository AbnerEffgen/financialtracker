package com.abner.financialtracker.controller;

import com.abner.financialtracker.dto.LoginRequest;
import com.abner.financialtracker.model.User;
import com.abner.financialtracker.repository.UserRepository;
import com.abner.financialtracker.security.JwtUtil;

// Importa a anotação @Autowired, que permite a injeção automática de dependências pelo Spring.
import org.springframework.beans.factory.annotation.Autowired;

// Importa a classe ResponseEntity, usada para retornar respostas HTTP completas (status, headers e body).
import org.springframework.http.ResponseEntity;

// Importa as anotações para criar controladores REST:
// @RestController para definir a classe como um controlador REST,
// @RequestMapping para mapear URLs,
// @GetMapping, @PostMapping, etc., para mapear métodos HTTP específicos.
import org.springframework.web.bind.annotation.*;

// Importa a interface Map, uma estrutura de dados que armazena pares chave-valor (ex: Map<String, Object>).
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = new JwtUtil();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElse(null);

        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciais Inválidas"));
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(Map.of(
                "message", "Login realizado com sucesso!",
                "token", token));
    }
}
