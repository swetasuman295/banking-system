package com.rabobank.banking.dto.request;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

/**
 * Request DTO for transaction to transfer money between accounts.
 * 
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Value
@Builder
@Schema(description = "Request to transfer money between accounts")
public final class TransferRequestDto {

	@NotBlank(message = "Source account ID is required")
	@Schema(description = "Source account ID", example = "ACC001", requiredMode = RequiredMode.REQUIRED )
	private String fromAccountId;

	@NotBlank(message = "Destination account ID is required")
	@Schema(description = "Destination account ID", example = "ACC002", requiredMode = RequiredMode.REQUIRED )
	private String toAccountId;

	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.01", message = "Amount must be greater than zero")
	@Schema(description = "Amount to transfer", example = "200.00", requiredMode = RequiredMode.REQUIRED )
	private BigDecimal amount;

	@NotBlank(message = "Card number is required")
	@Schema(description = "Card number (16 digits)", example = "4532123456781234", requiredMode = RequiredMode.REQUIRED )
	private String cardNumber;

	@Schema(description = "Transaction description", example = "Transfer to savings")
	private String description;
}