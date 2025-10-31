package com.rabobank.banking.domain.exception;
/**
 * Thrown when account ID doesn't exist in database.
 * 
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
public class AccountNotFoundException extends BankingException {
	private final String accountId;

	public AccountNotFoundException(String accountId) {
		super(String.format("Account not found with ID: %s", accountId), "ACCOUNT_NOT_FOUND");
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
