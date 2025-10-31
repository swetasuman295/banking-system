package com.rabobank.banking.domain.payment;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

/**
 * Debit card payment strategy - No transaction fees.
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Component
public class DebitCardPaymentStrategy implements CardPaymentStrategy {

    private static final BigDecimal NO_FEE = BigDecimal.ZERO;

    @Override
    public BigDecimal calculateTotalAmount(BigDecimal amount) {
        validateAmount(amount);
        return amount;
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return NO_FEE;
    }

    @Override
    public String getStrategyName() {
        return "DebitCardStrategy";
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}