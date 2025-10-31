package com.rabobank.banking.repository;

import com.rabobank.banking.domain.model.Account;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * AccountRepository to manage account entities in the banking system.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({
        @QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")  // 3 second timeout
    })
	@Query("SELECT a FROM Account a WHERE a.accountId = :accountId")
	Optional<Account> findByIdWithLock(@Param("accountId") String accountId);

	@Query("SELECT a FROM Account a JOIN FETCH a.user JOIN FETCH a.card WHERE a.active = true")
	List<Account> findAllActiveAccountsWithDetails();

	List<Account> findByUserId(Long userId);

	Optional<Account> findByAccountNumber(String accountNumber);

	boolean existsByAccountNumber(String accountNumber);
}
