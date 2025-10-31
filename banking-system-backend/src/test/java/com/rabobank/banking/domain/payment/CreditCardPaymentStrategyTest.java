package com.rabobank.banking.domain.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;


class CreditCardPaymentStrategyTest {

    private CreditCardPaymentStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new CreditCardPaymentStrategy();
    }

    @Test
    void calculateFee_OnePercentTest() {
        
        BigDecimal amount = new BigDecimal("100.00");

        
        BigDecimal fee = strategy.calculateFee(amount);

        
        assertEquals(new BigDecimal("1.00"), fee);
    }

    @Test
    void calculateTotalAmount_IncludesFeeTest() {
        
        BigDecimal amount = new BigDecimal("100.00");

        BigDecimal total = strategy.calculateTotalAmount(amount);

     
        assertEquals(new BigDecimal("101.00"), total);
    }

    @Test
    void calculateFee_RoundsCorrectlyTest() {

        BigDecimal amount = new BigDecimal("123.45");

        
        BigDecimal fee = strategy.calculateFee(amount);

        
        assertEquals(new BigDecimal("1.23"), fee);
    }

    @Test
    void tcalculateFee_NullAmount_ThrowsExceptionTest() {
   
        assertThrows(IllegalArgumentException.class, () -> {
            strategy.calculateFee(null);
        });
    }

    @Test
    void calculateFee_NegativeAmount_ThrowsExceptionTest() {
 
        assertThrows(IllegalArgumentException.class, () -> {
            strategy.calculateFee(new BigDecimal("-100.00"));
        });
    }

    @Test
    void getStrategyNameTest() {
        assertEquals("CreditCardStrategy", strategy.getStrategyName());
    }
}