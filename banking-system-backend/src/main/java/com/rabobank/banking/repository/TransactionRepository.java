package com.rabobank.banking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabobank.banking.domain.model.Transaction;


/**
 * TransactionRepository for managing transaction data in banking system.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0 
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
	List<Transaction> findByAccountIdOrderByTransactionDateDesc(String accountId);

	List<Transaction> findByAccountIdAndTransactionDateBetween(String accountId, LocalDateTime start,
			LocalDateTime end);

	boolean existsByTransactionId(String transactionId);
}
