package com.rabobank.banking.dto.request;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO for withdrawing money from account.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Value
@Builder
@Schema(description = "Request to withdraw money from account")
public final class WithdrawRequestDto {

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
}
