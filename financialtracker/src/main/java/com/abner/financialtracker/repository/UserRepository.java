package com.abner.financialtracker.repository;

// Importa a interface JpaRepository, que fornece métodos prontos para acessar o banco de dados
// (como findAll, save, delete, findById, etc.) com suporte a JPA.
import org.springframework.data.jpa.repository.JpaRepository;

// Permite escrever consultas personalizadas em JPQL ou SQL nativo dentro do repositório usando a anotação @Query.
import org.springframework.data.jpa.repository.Query;

// Marca a interface como um componente do tipo repositório (especialização de @Component).
// Isso permite que o Spring detecte e registre automaticamente essa interface como bean para injeção de dependência.
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.abner.financialtracker.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    Optional<User> findByUsername(String username);
}