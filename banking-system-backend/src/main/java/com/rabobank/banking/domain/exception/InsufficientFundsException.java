package com.rabobank.banking.domain.exception;

import java.math.BigDecimal;

/**
 * Thrown when account balance is insufficient for withdrawal/transfer
 * 
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
public class InsufficientFundsException extends BankingException {

	private final String accountId;
	private final BigDecimal availableBalance;
	private final BigDecimal requestedAmount;

	public InsufficientFundsException(String accountId, BigDecimal availableBalance, BigDecimal requestedAmount) {
		super(String.format("Insufficient funds in account %s. Available: €%.2f, Requested: €%.2f", accountId,
				availableBalance, requestedAmount), "INSUFFICIENT_FUNDS");
		this.accountId = accountId;
		this.availableBalance = availableBalance;
		this.requestedAmount = requestedAmount;
	}

	public String getAccountId() {
		return accountId;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}
}
