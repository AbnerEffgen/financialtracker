package com.abner.financialtracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abner.financialtracker.model.Transaction;
import com.abner.financialtracker.model.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

    List<Transaction> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
