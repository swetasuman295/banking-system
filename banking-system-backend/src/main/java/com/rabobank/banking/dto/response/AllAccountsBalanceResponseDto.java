package com.rabobank.banking.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Response containing all account balances.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Data
@Schema(description = "Response containing all account balances")
public class AllAccountsBalanceResponseDto {

	@Schema(description = "List of account balances")
	private List<AccountBalanceResponseDto> accounts;

	@Schema(description = "Total number of accounts", example = "3")
	private int totalAccounts;

	@Schema(description = "Total balance across all accounts", example = "4000.00")
	private BigDecimal totalBalance;

	@Schema(description = "Response timestamp", example = "2025-10-26T10:30:00")
	private LocalDateTime timestamp;

	public List<AccountBalanceResponseDto> getAccounts() {
		return accounts;
	}

	public int getTotalAccounts() {
		return totalAccounts;
	}

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setAccounts(List<AccountBalanceResponseDto> accounts) {
		this.accounts = accounts;
	}

	public void setTotalAccounts(int totalAccounts) {
		this.totalAccounts = totalAccounts;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public static AllAccountsBalanceResponseDtoBuilder builder() {
		return new AllAccountsBalanceResponseDtoBuilder();
	}

	public static class AllAccountsBalanceResponseDtoBuilder {
		private List<AccountBalanceResponseDto> accounts;
		private int totalAccounts;
		private BigDecimal totalBalance;
		private LocalDateTime timestamp;

		public AllAccountsBalanceResponseDtoBuilder accounts(List<AccountBalanceResponseDto> accounts) {
			this.accounts = accounts;
			return this;
		}

		public AllAccountsBalanceResponseDtoBuilder totalAccounts(int totalAccounts) {
			this.totalAccounts = totalAccounts;
			return this;
		}

		public AllAccountsBalanceResponseDtoBuilder totalBalance(BigDecimal totalBalance) {
			this.totalBalance = totalBalance;
			return this;
		}

		public AllAccountsBalanceResponseDtoBuilder timestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public AllAccountsBalanceResponseDto build() {
			AllAccountsBalanceResponseDto dto = new AllAccountsBalanceResponseDto();
			dto.accounts = this.accounts;
			dto.totalAccounts = this.totalAccounts;
			dto.totalBalance = this.totalBalance;
			dto.timestamp = this.timestamp;
			return dto;
		}
	}

}
