package com.rabobank.banking.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Account entity representing a bank account.
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

	@Id
	@Column(length = 20)
	@NotBlank(message = "Account ID is required")
	private String accountId;

	@Column(nullable = false, unique = true, length = 34)
	@NotBlank(message = "Account number is required")
	private String accountNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull(message = "User is required")
	private User user;

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Card card;

	@Column(nullable = false, precision = 19, scale = 2)
	@DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
	@Builder.Default
	private BigDecimal balance = BigDecimal.ZERO;

	@Column(nullable = false)
	@Builder.Default
	private boolean active = true;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public void withdraw(BigDecimal amount) {
		validateAmount(amount);

		if (balance.compareTo(amount) < 0) {
			throw new IllegalArgumentException(String.format("Insufficient funds. Available: €%.2f, Requested: €%.2f",
					balance.doubleValue(), amount.doubleValue()));
		}

		balance = balance.subtract(amount);
	}

	public void deposit(BigDecimal amount) {
		validateAmount(amount);
		balance = balance.add(amount);
	}

	private void validateAmount(BigDecimal amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount cannot be null");
		}
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException(
					String.format("Amount must be greater than zero. Provided: €%.2f", amount));
		}
	}

	public boolean hasSufficientFunds(BigDecimal amount) {
		return balance.compareTo(amount) >= 0;
	}

	public String getFormattedBalance() {
		return String.format("€%,.2f", balance);
	}
}