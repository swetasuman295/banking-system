package com.rabobank.banking.domain.payment;

import java.math.BigDecimal;

/**
 * Strategy interface for card payment processing. 
 * Different card types have different fee structures.
 */

public interface CardPaymentStrategy {
	BigDecimal calculateTotalAmount(BigDecimal amount);

	/**
	 * Calculate fee for the transaction.
	 */
	BigDecimal calculateFee(BigDecimal amount);

	/**
	 * Get strategy name for logging.
	 */
	String getStrategyName();
}
