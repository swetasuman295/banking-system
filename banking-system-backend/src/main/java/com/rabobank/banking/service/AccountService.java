package com.rabobank.banking.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabobank.banking.domain.exception.AccountNotFoundException;
import com.rabobank.banking.domain.model.Account;
import com.rabobank.banking.domain.model.Card;
import com.rabobank.banking.dto.response.AccountBalanceResponseDto;
import com.rabobank.banking.dto.response.AllAccountsBalanceResponseDto;
import com.rabobank.banking.repository.AccountRepository;

/**
 * Provides operations related to viewing account information.
 *
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Service
public class AccountService {
	private static final Logger log = LoggerFactory.getLogger(AccountService.class);
	private final AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	/**
	 * Fetches balances for all active accounts.
	 * 
	 * @return DTO containing list of account balances, total count, and total
	 *         balance
	 */
	@Transactional(readOnly = true)
	public AllAccountsBalanceResponseDto getAllAccountBalances() {
		log.info("Fetching all account balances");

		List<Account> accounts = accountRepository.findAllActiveAccountsWithDetails();

		List<AccountBalanceResponseDto> accountBalances = accounts.stream().map(this::mapToAccountBalanceResponse)
				.toList();

		BigDecimal totalBalance = accounts.stream().map(Account::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);

		log.info("Found {} active accounts with total balance: €{}", accounts.size(), totalBalance);

		return AllAccountsBalanceResponseDto.builder().accounts(accountBalances).totalAccounts(accounts.size())
				.totalBalance(totalBalance).timestamp(LocalDateTime.now()).build();
	}

	/**
	 * Fetches an account by its ID.
	 * 
	 * @param accountId
	 * @return the account entity
	 * @throws AccountNotFoundException if account with given ID doesn't exist
	 */
	@Transactional(readOnly = true)
	public Account getAccountById(String accountId) {
		log.debug("Fetching account: {}", accountId);
		return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	/**
	 * Fetches an account by its ID with a Lock for update
	 * 
	 * @param accountId
	 * @return
	 */
	@Transactional
	public Account getAccountByIdWithLock(String accountId) {
		log.debug("Fetching account with lock: {}", accountId);
		return accountRepository.findByIdWithLock(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	/**
	 * Maps Account entity to AccountBalanceResponseDTO.
	 * 
	 * @param account
	 * @return
	 */
	private AccountBalanceResponseDto mapToAccountBalanceResponse(Account account) {
		Card card = account.getCard();
		return AccountBalanceResponseDto.builder().accountId(account.getAccountId())
				.accountNumber(account.getAccountNumber()).userName(account.getUser().getFullName())
				.userEmail(account.getUser().getEmail()).balance(account.getBalance())
				.cardType(card != null ? card.getCardType() : null)
				.cardNumber(card != null ? card.getMaskedCardNumber() : "No card").active(account.isActive()).build();
	}
}