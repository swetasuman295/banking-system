package com.rabobank.banking.service;

import com.rabobank.banking.domain.exception.AccountNotFoundException;
import com.rabobank.banking.domain.model.Account;
import com.rabobank.banking.domain.model.Card;
import com.rabobank.banking.domain.model.CardType;
import com.rabobank.banking.domain.model.User;
import com.rabobank.banking.dto.response.AccountBalanceResponseDto;
import com.rabobank.banking.dto.response.AllAccountsBalanceResponseDto;
import com.rabobank.banking.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private User testUser1;
    private User testUser2;
    private Account testAccount1;
    private Account testAccount2;
    private Card testCard1;
    private Card testCard2;

    @BeforeEach
    void setUp() {
        testUser1 = User.builder()
            .id(1L)
            .firstName("Ankit")
            .lastName("Kumar")
            .email("Ankitkumar@gmail.com")
            .phoneNumber("+31612345678")
            .build();

        testUser2 = User.builder()
            .id(2L)
            .firstName("Sweta")
            .lastName("Suman")
            .email("Swetasuman295@gmail.com")
            .phoneNumber("+31687654321")
            .build();

        testCard1 = Card.builder()
            .id(1L)
            .cardNumber("4532123456781234")
            .cardType(CardType.DEBIT)
            .expiryDate(LocalDate.now().plusYears(2))
            .cardHolderName("ANKIT KUMAR")
            .active(true)
            .build();

        testCard2 = Card.builder()
            .id(2L)
            .cardNumber("5412345678901234")
            .cardType(CardType.CREDIT)
            .expiryDate(LocalDate.now().plusYears(2))
            .cardHolderName("SWETA SUMAN")
            .active(true)
            .build();

        testAccount1 = Account.builder()
            .accountId("ACC001")
            .accountNumber("NL91RABO0417164300")
            .user(testUser1)
            .card(testCard1)
            .balance(new BigDecimal("1500.00"))
            .active(true)
            .build();

        testAccount2 = Account.builder()
            .accountId("ACC002")
            .accountNumber("NL91RABO0417164301")
            .user(testUser2)
            .card(testCard2)
            .balance(new BigDecimal("2500.00"))
            .active(true)
            .build();

        testCard1.setAccount(testAccount1);
        testCard2.setAccount(testAccount2);
    }

    @Test
    void getAllAccountBalances_SuccessTest() {
        // Arrange
        List<Account> accounts = Arrays.asList(testAccount1, testAccount2);
        when(accountRepository.findAllActiveAccountsWithDetails()).thenReturn(accounts);

        // Act
        AllAccountsBalanceResponseDto response = accountService.getAllAccountBalances();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getTotalAccounts());
        assertEquals(new BigDecimal("4000.00"), response.getTotalBalance());
        assertEquals(2, response.getAccounts().size());
        
        AccountBalanceResponseDto acc1 = response.getAccounts().get(0);
        assertEquals("ACC001", acc1.getAccountId());
        assertEquals("NL91RABO0417164300", acc1.getAccountNumber());
        assertEquals("Ankit Kumar", acc1.getUserName());
        assertEquals("Ankitkumar@gmail.com", acc1.getUserEmail());
        assertEquals(new BigDecimal("1500.00"), acc1.getBalance());
        assertEquals(CardType.DEBIT, acc1.getCardType());
        assertTrue(acc1.isActive());

        verify(accountRepository, times(1)).findAllActiveAccountsWithDetails();
    }

    @Test
    void getAllAccountBalances_EmptyListTest() {
        
        when(accountRepository.findAllActiveAccountsWithDetails()).thenReturn(Collections.emptyList());

        
        AllAccountsBalanceResponseDto response = accountService.getAllAccountBalances();

        
        assertNotNull(response);
        assertEquals(0, response.getTotalAccounts());
        assertEquals(BigDecimal.ZERO, response.getTotalBalance());
        assertTrue(response.getAccounts().isEmpty());
    }

    @Test
    void getAllAccountBalances_SingleAccountTest() {
        
        List<Account> accounts = Collections.singletonList(testAccount1);
        when(accountRepository.findAllActiveAccountsWithDetails()).thenReturn(accounts);

        
        AllAccountsBalanceResponseDto response = accountService.getAllAccountBalances();

        
        assertNotNull(response);
        assertEquals(1, response.getTotalAccounts());
        assertEquals(new BigDecimal("1500.00"), response.getTotalBalance());
    }

    @Test
    void getAccountById_SuccessTest() {
        
        when(accountRepository.findById("ACC001")).thenReturn(Optional.of(testAccount1));

        
        Account account = accountService.getAccountById("ACC001");

        
        assertNotNull(account);
        assertEquals("ACC001", account.getAccountId());
        assertEquals("NL91RABO0417164300", account.getAccountNumber());
        assertEquals(new BigDecimal("1500.00"), account.getBalance());
        verify(accountRepository, times(1)).findById("ACC001");
    }

    @Test
    void getAccountById_NotFound_ThrowsExceptionTest() {
       
        when(accountRepository.findById("ACC999")).thenReturn(Optional.empty());

        
        AccountNotFoundException exception = assertThrows(
            AccountNotFoundException.class,
            () -> accountService.getAccountById("ACC999")
        );

        assertTrue(exception.getMessage().contains("ACC999"));
        verify(accountRepository, times(1)).findById("ACC999");
    }

    @Test
    void getAccountByIdWithLock_SuccessTest() {
       
        when(accountRepository.findByIdWithLock("ACC001")).thenReturn(Optional.of(testAccount1));

        
        Account account = accountService.getAccountByIdWithLock("ACC001");

        
        assertNotNull(account);
        assertEquals("ACC001", account.getAccountId());
        verify(accountRepository, times(1)).findByIdWithLock("ACC001");
    }

    @Test
    void getAccountByIdWithLock_NotFound_ThrowsExceptionTest() {
        
        when(accountRepository.findByIdWithLock("ACC999")).thenReturn(Optional.empty());

       
        assertThrows(
            AccountNotFoundException.class,
            () -> accountService.getAccountByIdWithLock("ACC999")
        );
    }

    @Test
    void getAllAccountBalances_VerifyMaskedCardNumberTest() {
        
        List<Account> accounts = Collections.singletonList(testAccount1);
        when(accountRepository.findAllActiveAccountsWithDetails()).thenReturn(accounts);

       
        AllAccountsBalanceResponseDto response = accountService.getAllAccountBalances();

       
        AccountBalanceResponseDto account = response.getAccounts().get(0);
        assertEquals("**** **** **** 1234", account.getCardNumber());
        assertNotEquals("4532123456781234", account.getCardNumber()); 
    }

    @Test
    void getAllAccountBalances_CalculatesTotalBalanceCorrectlyTest() {
    	Card testCard3 = Card.builder()
    	        .id(3L)
    	        .cardNumber("6011123456789012")
    	        .cardType(CardType.DEBIT)
    	        .expiryDate(LocalDate.now().plusYears(2))
    	        .cardHolderName("JOHN DOE")
    	        .active(true)
    	        .build();
        Account account3 = Account.builder()
            .accountId("ACC003")
            .accountNumber("NL91RABO0417164302")
            .user(testUser1)
            .card(testCard3)
            .balance(new BigDecimal("750.50"))
            .active(true)
            .build();
        testCard3.setAccount(account3);
        List<Account> accounts = Arrays.asList(testAccount1, testAccount2, account3);
        when(accountRepository.findAllActiveAccountsWithDetails()).thenReturn(accounts);

       
        AllAccountsBalanceResponseDto response = accountService.getAllAccountBalances();

        assertEquals(new BigDecimal("4750.50"), response.getTotalBalance());
    }
}