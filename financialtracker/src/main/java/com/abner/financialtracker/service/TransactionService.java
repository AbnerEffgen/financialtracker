package com.abner.financialtracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abner.financialtracker.model.Transaction;
import com.abner.financialtracker.model.User;
import com.abner.financialtracker.repository.TransactionRepository;

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
        return repository.findByUserAndDataBetween(user, startDate, endDate);
    }

    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
