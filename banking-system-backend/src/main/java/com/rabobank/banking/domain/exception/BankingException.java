package com.rabobank.banking.domain.exception;

/**
 * Base exception for all banking-specific exceptions.
 * 
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
public class BankingException extends RuntimeException {
	private final String errorCode;

	public BankingException(String message) {
		super(message);
		this.errorCode = "BANKING_ERROR";
	}

	public BankingException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BankingException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = "BANKING_ERROR";
	}

	public BankingException(String message, String errorCode, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
