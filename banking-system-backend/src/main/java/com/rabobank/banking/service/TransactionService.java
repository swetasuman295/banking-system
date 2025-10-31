package com.rabobank.banking.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabobank.banking.domain.exception.InsufficientFundsException;
import com.rabobank.banking.domain.exception.InvalidCardException;
import com.rabobank.banking.domain.exception.InvalidTransactionException;
import com.rabobank.banking.domain.model.Account;
import com.rabobank.banking.domain.model.Card;
import com.rabobank.banking.domain.model.CardType;
import com.rabobank.banking.domain.model.Transaction;
import com.rabobank.banking.domain.payment.CardPaymentStrategy;
import com.rabobank.banking.domain.payment.CreditCardPaymentStrategy;
import com.rabobank.banking.domain.payment.DebitCardPaymentStrategy;
import com.rabobank.banking.dto.request.TransferRequestDto;
import com.rabobank.banking.dto.request.WithdrawRequestDto;
import com.rabobank.banking.dto.response.TransactionResponseDto;
import com.rabobank.banking.repository.CardRepository;
import com.rabobank.banking.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles core business logic for processing transactions, including
 * withdrawals and transfers between accounts.
 *
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

	private final AccountService accountService;
	private final CardRepository cardRepository;
	private final TransactionRepository transactionRepository;
	private final DebitCardPaymentStrategy debitCardStrategy;
	private final CreditCardPaymentStrategy creditCardStrategy;

	/**
	 * Withdraws money from an account using a debit or credit card. Adds a 1% fee
	 * for credit cards and checks balance before processing.
	 */
	@Transactional
	public TransactionResponseDto withdraw(WithdrawRequestDto request) {
		log.info("Processing withdrawal: accountId={}, amount={}", request.getAccountId(), request.getAmount());

		Account account = accountService.getAccountByIdWithLock(request.getAccountId());
		Card card = validateCard(request.getCardNumber(), account);

		CardPaymentStrategy strategy = getStrategyForCardType(card.getCardType());
		BigDecimal fee = strategy.calculateFee(request.getAmount());
		BigDecimal totalAmount = strategy.calculateTotalAmount(request.getAmount());

		if (!account.hasSufficientFunds(totalAmount)) {
			throw new InsufficientFundsException(account.getAccountId(), account.getBalance(), totalAmount);
		}

		BigDecimal balanceBefore = account.getBalance();
		account.withdraw(totalAmount);
		BigDecimal balanceAfter = account.getBalance();

		Transaction transaction = createTransaction(account.getAccountId(), null,
				Transaction.TransactionType.WITHDRAWAL, request.getAmount(), fee, totalAmount, card.getCardType(),
				balanceBefore, balanceAfter, request.getDescription());

		transactionRepository.save(transaction);

		log.info("Withdrawal successful: transactionId={}, totalAmount={}", transaction.getTransactionId(),
				totalAmount);

		return mapToTransactionResponse(transaction);
	}

	/**
	 * Transfers money between two accounts. Ensures both accounts are valid and
	 * prevents same-account transfers.
	 */
	@Transactional
	public TransactionResponseDto transfer(TransferRequestDto request) {
		log.info("Processing transfer: from={}, to={}, amount={}", request.getFromAccountId(), request.getToAccountId(),
				request.getAmount());

		if (request.getFromAccountId().equals(request.getToAccountId())) {
			throw new InvalidTransactionException("Cannot transfer to the same account");
		}

		Account fromAccount = accountService.getAccountByIdWithLock(request.getFromAccountId());
		Account toAccount = accountService.getAccountByIdWithLock(request.getToAccountId());
		Card card = validateCard(request.getCardNumber(), fromAccount);

		CardPaymentStrategy strategy = getStrategyForCardType(card.getCardType());
		BigDecimal fee = strategy.calculateFee(request.getAmount());
		BigDecimal totalAmount = strategy.calculateTotalAmount(request.getAmount());

		if (!fromAccount.hasSufficientFunds(totalAmount)) {
			throw new InsufficientFundsException(fromAccount.getAccountId(), fromAccount.getBalance(), totalAmount);
		}

		BigDecimal balanceBefore = fromAccount.getBalance();
		fromAccount.withdraw(totalAmount);
		toAccount.deposit(request.getAmount());
		BigDecimal balanceAfter = fromAccount.getBalance();

		Transaction transaction = createTransaction(fromAccount.getAccountId(), toAccount.getAccountId(),
				Transaction.TransactionType.TRANSFER, request.getAmount(), fee, totalAmount, card.getCardType(),
				balanceBefore, balanceAfter, request.getDescription());

		transactionRepository.save(transaction);

		log.info("Transfer successful: transactionId={}, totalAmount={}", transaction.getTransactionId(), totalAmount);

		return mapToTransactionResponse(transaction);
	}

	/**
	 * Validates that the provided card belongs to the given account and is still
	 * valid for use.
	 * 
	 * @param cardNumber
	 * @param account
	 * @return
	 */
	private Card validateCard(String cardNumber, Account account) {
		Card card = cardRepository.findByCardNumber(cardNumber)
				.orElseThrow(() -> new InvalidCardException("Card not found"));

		if (!card.getAccount().getAccountId().equals(account.getAccountId())) {
			throw new InvalidCardException("Card does not belong to this account");
		}

		if (!card.isValidForTransaction()) {
			throw new InvalidCardException("Card is not valid for transactions");
		}

		return card;
	}

	private CardPaymentStrategy getStrategyForCardType(CardType cardType) {
		return cardType == CardType.DEBIT ? debitCardStrategy : creditCardStrategy;
	}

	/**
	 * Creates a transaction record with all details like amount, fee, and balances.
	 * 
	 * @param accountId
	 * @param toAccountId
	 * @param type
	 * @param amount
	 * @param fee
	 * @param totalAmount
	 * @param cardType
	 * @param balanceBefore
	 * @param balanceAfter
	 * @param description
	 * @return
	 */
	private Transaction createTransaction(String accountId, String toAccountId, Transaction.TransactionType type,
			BigDecimal amount, BigDecimal fee, BigDecimal totalAmount, CardType cardType, BigDecimal balanceBefore,
			BigDecimal balanceAfter, String description) {
		return Transaction.builder().transactionId(generateTransactionId()).accountId(accountId)
				.toAccountId(toAccountId).type(type).amount(amount).fee(fee).totalAmount(totalAmount).cardType(cardType)
				.balanceBefore(balanceBefore).balanceAfter(balanceAfter).description(description)
				.status(Transaction.TransactionStatus.SUCCESS).build();
	}

	/**
	 * Generates a unique transaction ID with date and sequence number.
	 */
	private String generateTransactionId() {
		return "TXN-" + UUID.randomUUID().toString();
	}

	/**
	 * Converts a Transaction entity into a response DTO for API output.
	 * 
	 * @param transaction
	 * @return
	 */
	private TransactionResponseDto mapToTransactionResponse(Transaction transaction) {
		return TransactionResponseDto.builder().transactionId(transaction.getTransactionId())
				.accountId(transaction.getAccountId()).toAccountId(transaction.getToAccountId())
				.type(transaction.getType()).amount(transaction.getAmount()).fee(transaction.getFee())
				.totalAmount(transaction.getTotalAmount()).cardType(transaction.getCardType())
				.balanceBefore(transaction.getBalanceBefore()).balanceAfter(transaction.getBalanceAfter())
				.description(transaction.getDescription()).status(transaction.getStatus())
				.timestamp(transaction.getTransactionDate()).build();
	}
}