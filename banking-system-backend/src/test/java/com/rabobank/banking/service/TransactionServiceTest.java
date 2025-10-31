package com.rabobank.banking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rabobank.banking.domain.exception.InsufficientFundsException;
import com.rabobank.banking.domain.exception.InvalidCardException;
import com.rabobank.banking.domain.exception.InvalidTransactionException;
import com.rabobank.banking.domain.model.Account;
import com.rabobank.banking.domain.model.Card;
import com.rabobank.banking.domain.model.CardType;
import com.rabobank.banking.domain.model.Transaction;
import com.rabobank.banking.domain.model.User;
import com.rabobank.banking.domain.payment.CreditCardPaymentStrategy;
import com.rabobank.banking.domain.payment.DebitCardPaymentStrategy;
import com.rabobank.banking.dto.request.TransferRequestDto;
import com.rabobank.banking.dto.request.WithdrawRequestDto;
import com.rabobank.banking.dto.response.TransactionResponseDto;
import com.rabobank.banking.repository.CardRepository;
import com.rabobank.banking.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private DebitCardPaymentStrategy debitCardStrategy;

    @Mock
    private CreditCardPaymentStrategy creditCardStrategy;

    @InjectMocks
    private TransactionService transactionService;

    private Account testAccount;
    private Card testCard;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
            .id(1L)
            .firstName("Sweta")
            .lastName("Suman")
            .email("Swetasuman295@gmail.com")
            .phoneNumber("+316447514")
            .build();

        testAccount = Account.builder()
            .accountId("ACC001")
            .accountNumber("NL91RABO0417164300")
            .user(testUser)
            .balance(new BigDecimal("1000.00"))
            .active(true)
            .build();

        testCard = Card.builder()
            .id(1L)
            .cardNumber("4532123456781234")
            .cardType(CardType.DEBIT)
            .account(testAccount)
            .expiryDate(LocalDate.now().plusYears(2))
            .cardHolderName("Sweta Suman")
            .active(true)
            .build();

        testAccount.setCard(testCard);
    }

    @Test
    void withdrawWithDebitCard_SuccessTest() {
        WithdrawRequestDto request = WithdrawRequestDto.builder()
            .accountId("ACC001")
            .amount(new BigDecimal("100.00"))
            .cardNumber("4532123456781234")
            .description("ATM Withdrawal")
            .build();

        when(accountService.getAccountByIdWithLock("ACC001")).thenReturn(testAccount);
        when(cardRepository.findByCardNumber("4532123456781234")).thenReturn(Optional.of(testCard));
        when(debitCardStrategy.calculateFee(any())).thenReturn(BigDecimal.ZERO);
        when(debitCardStrategy.calculateTotalAmount(any())).thenReturn(new BigDecimal("100.00"));
        when(transactionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        TransactionResponseDto response = transactionService.withdraw(request);

        assertNotNull(response);
        assertEquals(new BigDecimal("100.00"), response.getAmount());
        assertEquals(BigDecimal.ZERO, response.getFee());
        assertEquals(new BigDecimal("100.00"), response.getTotalAmount());
        assertEquals(CardType.DEBIT, response.getCardType());
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    void withdrawWithCreditCard_SuccessTest() {
        testCard.setCardType(CardType.CREDIT);
        
        WithdrawRequestDto request = WithdrawRequestDto.builder()
            .accountId("ACC001")
            .amount(new BigDecimal("100.00"))
            .cardNumber("4532123456781234")
            .build();

        when(accountService.getAccountByIdWithLock("ACC001")).thenReturn(testAccount);
        when(cardRepository.findByCardNumber("4532123456781234")).thenReturn(Optional.of(testCard));
        when(creditCardStrategy.calculateFee(any())).thenReturn(new BigDecimal("1.00"));
        when(creditCardStrategy.calculateTotalAmount(any())).thenReturn(new BigDecimal("101.00"));
        when(transactionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        TransactionResponseDto response = transactionService.withdraw(request);

        assertNotNull(response);
        assertEquals(new BigDecimal("100.00"), response.getAmount());
        assertEquals(new BigDecimal("1.00"), response.getFee());
        assertEquals(new BigDecimal("101.00"), response.getTotalAmount());
    }

    @Test
    void withdraw_InsufficientFundsTest() {
        WithdrawRequestDto request = WithdrawRequestDto.builder()
            .accountId("ACC001")
            .amount(new BigDecimal("2000.00"))
            .cardNumber("4532123456781234")
            .build();

        when(accountService.getAccountByIdWithLock("ACC001")).thenReturn(testAccount);
        when(cardRepository.findByCardNumber("4532123456781234")).thenReturn(Optional.of(testCard));
        when(debitCardStrategy.calculateFee(any())).thenReturn(BigDecimal.ZERO);
        when(debitCardStrategy.calculateTotalAmount(any())).thenReturn(new BigDecimal("2000.00"));

        assertThrows(InsufficientFundsException.class, () -> {
            transactionService.withdraw(request);
        });

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void withdraw_InvalidCardTest() {
        WithdrawRequestDto request = WithdrawRequestDto.builder()
            .accountId("ACC001")
            .amount(new BigDecimal("100.00"))
            .cardNumber("9999999999999999")
            .build();

        when(accountService.getAccountByIdWithLock("ACC001")).thenReturn(testAccount);
        when(cardRepository.findByCardNumber("9999999999999999")).thenReturn(Optional.empty());

        assertThrows(InvalidCardException.class, () -> {
            transactionService.withdraw(request);
        });
    }

    @Test
    void transfer_SuccessTest() {
        Account toAccount = Account.builder()
            .accountId("ACC002")
            .accountNumber("NL91RABO0417164301")
            .user(testUser)
            .balance(new BigDecimal("500.00"))
            .active(true)
            .build();

        TransferRequestDto request = TransferRequestDto.builder()
            .fromAccountId("ACC001")
            .toAccountId("ACC002")
            .amount(new BigDecimal("200.00"))
            .cardNumber("4532123456781234")
            .build();

        when(accountService.getAccountByIdWithLock("ACC001")).thenReturn(testAccount);
        when(accountService.getAccountByIdWithLock("ACC002")).thenReturn(toAccount);
        when(cardRepository.findByCardNumber("4532123456781234")).thenReturn(Optional.of(testCard));
        when(debitCardStrategy.calculateFee(any())).thenReturn(BigDecimal.ZERO);
        when(debitCardStrategy.calculateTotalAmount(any())).thenReturn(new BigDecimal("200.00"));
        when(transactionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        TransactionResponseDto response = transactionService.transfer(request);

        assertNotNull(response);
        assertEquals("ACC001", response.getAccountId());
        assertEquals("ACC002", response.getToAccountId());
        assertEquals(Transaction.TransactionType.TRANSFER, response.getType());
    }

    @Test
    void transfer_SameAccountTest() {
        TransferRequestDto request = TransferRequestDto.builder()
            .fromAccountId("ACC001")
            .toAccountId("ACC001")
            .amount(new BigDecimal("200.00"))
            .cardNumber("4532123456781234")
            .build();

        assertThrows(InvalidTransactionException.class, () -> {
            transactionService.transfer(request);
        });
    }
}