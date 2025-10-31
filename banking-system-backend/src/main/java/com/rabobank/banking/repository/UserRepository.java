package com.rabobank.banking.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabobank.banking.domain.model.User;

/**
 * UserRepository for managing user entities in the banking systems.
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);
}
