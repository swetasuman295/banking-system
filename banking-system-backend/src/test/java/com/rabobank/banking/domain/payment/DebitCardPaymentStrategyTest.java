package com.rabobank.banking.domain.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class DebitCardPaymentStrategyTest {

    private DebitCardPaymentStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new DebitCardPaymentStrategy();
    }

    @Test
    void calculateFee_AlwaysZeroTest() {
        
        BigDecimal amount = new BigDecimal("100.00");

       
        BigDecimal fee = strategy.calculateFee(amount);

        
        assertEquals(BigDecimal.ZERO, fee);
    }

    @Test
    void calculateTotalAmount_SameAsAmountTest() {
       
        BigDecimal amount = new BigDecimal("100.00");

       
        BigDecimal total = strategy.calculateTotalAmount(amount);

        
        assertEquals(amount, total);
    }

    @Test
    void getStrategyNameTest() {
        assertEquals("DebitCardStrategy", strategy.getStrategyName());
    }
}