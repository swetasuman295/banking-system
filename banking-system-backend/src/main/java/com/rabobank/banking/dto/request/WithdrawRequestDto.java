package com.rabobank.banking.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * DTO for withdrawing money from account.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to withdraw money from account")
public class WithdrawRequestDto {

    @NotBlank(message = "Account ID is required")
    @Schema(description = "Account ID", example = "ACC001", requiredMode = RequiredMode.REQUIRED )
    private String accountId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    @Schema(description = "Amount to withdraw", example = "100.00", requiredMode = RequiredMode.REQUIRED )
    private BigDecimal amount;

    @NotBlank(message = "Card number is required")
    @Schema(description = "Card number (16 digits)", example = "4532123456781234", requiredMode = RequiredMode.REQUIRED )
    private String cardNumber;

    @Schema(description = "Transaction description", example = "ATM Withdrawal")
    private String description;

	public String getAccountId() {
		return accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
