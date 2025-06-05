package com.abner.financialtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.abner.financialtracker.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    Optional<User> findByUsername(String username);
}