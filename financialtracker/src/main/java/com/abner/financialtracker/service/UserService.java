package com.abner.financialtracker.service;

// Importa a interface List, uma coleção ordenada que permite elementos duplicados.
// Muito usada para retornar listas de objetos (ex: List<Usuario>).
import java.util.List;

// Importa a classe Optional, usada para representar um valor que pode ou não estar presente.
// Ajuda a evitar null pointer exceptions ao buscar dados (ex: Optional<Usuario>).
import java.util.Optional;

// Importa a anotação @Service, que marca a classe como um componente de serviço do Spring.
// Indica que ela contém lógica de negócio e será gerenciada como um bean pelo Spring.
import org.springframework.stereotype.Service;

import com.abner.financialtracker.model.User;
import com.abner.financialtracker.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        if (user.getUsername() == null) {
            throw new RuntimeException("Username não pode ser nulo");
        }

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Usuário já existe");
        }

        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("ID não encontado");
        }
        userRepository.deleteById(id);
    }
}
