package com.rabobank.banking.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Card entity representing a payment card (Debit or Credit).
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Entity
@Table(name = "cards", uniqueConstraints = { @UniqueConstraint(name = "uk_card_account", columnNames = "account_id")

})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 16)
	@NotBlank(message = "Card number is required")
	private String cardNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	@NotNull(message = "Card type is required")
	private CardType cardType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false, unique = true)
	@NotNull(message = "Account is required")
	private Account account;

	@Column(nullable = false)
	@NotNull(message = "Expiry date is required")
	private LocalDate expiryDate;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "Card holder name is required")
	private String cardHolderName;

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

	public boolean isExpired() {
		return LocalDate.now().isAfter(expiryDate);
	}

	public boolean isValidForTransaction() {
		return active && !isExpired();
	}

	public String getMaskedCardNumber() {
		if (cardNumber == null || cardNumber.length() < 4) {
			return "****";
		}
		return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
	}

	public String getCardBrand() {
		if (cardNumber == null || cardNumber.isEmpty()) {
			return "Unknown";
		}

		char firstDigit = cardNumber.charAt(0);
		return switch (firstDigit) {
		case '4' -> "Visa";
		case '5' -> "Mastercard";
		case '3' -> "American Express";
		default -> "Unknown";
		};
	}
}