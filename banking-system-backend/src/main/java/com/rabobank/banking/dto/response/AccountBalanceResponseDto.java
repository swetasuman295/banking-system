package com.rabobank.banking.dto.response;

import java.math.BigDecimal;

import com.rabobank.banking.domain.model.CardType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;


/**
 * Account balance response DTO.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Value
@Builder
@Schema(description = "Account balance information")
public final class AccountBalanceResponseDto {

	@Schema(description = "Account ID", example = "ACC001")
	private String accountId;

	@Schema(description = "Account number (IBAN)", example = "NL91RABO0417164300")
	private String accountNumber;

	@Schema(description = "Account holder name", example = "John Doe")
	private String userName;

	@Schema(description = "Account holder email", example = "john.doe@example.com")
	private String userEmail;

	@Schema(description = "Current balance", example = "1500.00")
	private BigDecimal balance;

	@Schema(description = "Card type", example = "DEBIT")
	private CardType cardType;

	@Schema(description = "Masked card number", example = "**** **** **** 1234")
	private String cardNumber;

	@Schema(description = "Account active status", example = "true")
	private boolean active;

}