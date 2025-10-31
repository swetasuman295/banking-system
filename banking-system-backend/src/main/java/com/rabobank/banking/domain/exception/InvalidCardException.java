package com.rabobank.banking.domain.exception;
/**
 * Thrown when card is invalid, expired, or doesn't match account.
 * 
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
public class InvalidCardException extends BankingException {
	private final String cardNumber;

    public InvalidCardException(String message) {
        super(message, "INVALID_CARD");
        this.cardNumber = null;
    }

    public InvalidCardException(String message, String cardNumber) {
        super(message, "INVALID_CARD");
        this.cardNumber = maskCardNumber(cardNumber);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    // Never expose full card number in exception
    private static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "**** " + cardNumber.substring(cardNumber.length() - 4);
    }
}
