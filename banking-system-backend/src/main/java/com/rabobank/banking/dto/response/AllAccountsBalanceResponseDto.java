package com.rabobank.banking.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Response containing all account balances.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Value
@Builder
@Schema(description = "Response containing all account balances")
public final class AllAccountsBalanceResponseDto {

	@Schema(description = "List of account balances")
	private List<AccountBalanceResponseDto> accounts;

	@Schema(description = "Total number of accounts", example = "3")
	private int totalAccounts;

	@Schema(description = "Total balance across all accounts", example = "4000.00")
	private BigDecimal totalBalance;

	@Schema(description = "Response timestamp", example = "2025-10-26T10:30:00")
	private LocalDateTime timestamp;

}
