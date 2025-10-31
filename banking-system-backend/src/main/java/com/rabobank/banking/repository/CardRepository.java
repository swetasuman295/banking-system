package com.rabobank.banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabobank.banking.domain.model.Card;

/**
 * CardRepository for card entities in the banking systems.
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

	Optional<Card> findByCardNumber(String cardNumber);

	Optional<Card> findByAccountAccountId(String accountId);

	boolean existsByCardNumber(String cardNumber);
}
