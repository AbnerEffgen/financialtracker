package com.abner.financialtracker.service;

import java.util.List;
import java.util.Optional;
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
