package com.rabobank.banking.domain.exception;

/**
 * Thrown when transaction is invalid for business reasons
 * 
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */

public class InvalidTransactionException extends BankingException{
	public InvalidTransactionException(String message) {
        super(message, "INVALID_TRANSACTION");
    }

    public InvalidTransactionException(String message, Throwable cause) {
        super(message, "INVALID_TRANSACTION", cause);
    }
}
