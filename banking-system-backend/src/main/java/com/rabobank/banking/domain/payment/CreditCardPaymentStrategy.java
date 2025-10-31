package com.rabobank.banking.domain.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Credit card payment strategy implementation.
 * Applies 1% fee on all transactions as per business requirement.
 * 
 * Example:
 * - Transaction amount: €100.00
 * - Fee (1%): €1.00
 * - Total charged: €101.00
 */
@Component
public class CreditCardPaymentStrategy implements CardPaymentStrategy {

    private static final BigDecimal FEE_PERCENTAGE = new BigDecimal("0.01"); // 1%
    private static final int SCALE = 2; // 2 decimal places for currency
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    @Override
    public BigDecimal calculateTotalAmount(BigDecimal amount) {
        validateAmount(amount);
        BigDecimal fee = calculateFee(amount);
        return amount.add(fee);
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        validateAmount(amount);
        return amount.multiply(FEE_PERCENTAGE).setScale(SCALE, ROUNDING_MODE);
    }

    @Override
    public String getStrategyName() {
        return "CreditCardStrategy";
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