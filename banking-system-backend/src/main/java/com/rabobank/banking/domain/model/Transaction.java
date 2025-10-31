package com.rabobank.banking.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction entity for complete audit trail.
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Entity
@Table(name = "transactions", indexes = { @Index(name = "idx_account_id", columnList = "account_id"),
		@Index(name = "idx_transaction_date", columnList = "transactionDate"),
		@Index(name = "idx_type", columnList = "type") }

)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
	@Id
	@Column(length = 50)
	private String transactionId;

	@Column(nullable = false, length = 20)
	private String accountId;

	@Column(length = 20)
	private String toAccountId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TransactionType type;

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal amount;

	@Column(nullable = false, precision = 15, scale = 2)
	@Builder.Default
	private BigDecimal fee = BigDecimal.ZERO;

	@Column(nullable = false, precision = 15, scale = 2)
	private BigDecimal totalAmount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private CardType cardType;

	@Column(precision = 15, scale = 2)
	private BigDecimal balanceBefore;

	@Column(precision = 15, scale = 2)
	private BigDecimal balanceAfter;

	@Column(length = 255)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	@Builder.Default
	private TransactionStatus status = TransactionStatus.SUCCESS;

	@Column(nullable = false, updatable = false)
	private LocalDateTime transactionDate;

	@PrePersist
	protected void onCreate() {
		transactionDate = LocalDateTime.now();
	}

	public enum TransactionType {
		WITHDRAWAL, 
		TRANSFER 
	}

	public enum TransactionStatus {
		SUCCESS, 
		FAILED, 
		PENDING 
	}

	public BigDecimal getEffectiveAmount() {
		return totalAmount;
	}

	public boolean isSuccessful() {
		return TransactionStatus.SUCCESS.equals(status);
	}

	public String getSummary() {
		return String.format("Transaction[id=%s, type=%s, amount=€%.2f, fee=€%.2f, status=%s]", transactionId, type,
				amount, fee, status);
	}

}
