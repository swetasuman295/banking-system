package com.rabobank.banking.dto.response;

import java.math.BigDecimal;

import com.rabobank.banking.domain.model.CardType;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * Account balance response DTO.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Schema(description = "Account balance information")
public class AccountBalanceResponseDto {

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

	public String getAccountId() {
		return accountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public CardType getCardType() {
		return cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public boolean isActive() {
		return active;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public static AccountBalanceResponseDtoBuilder builder() {
		
		return new AccountBalanceResponseDtoBuilder();
	}
	public static class AccountBalanceResponseDtoBuilder {
		private String accountId;
		private String accountNumber;
		private String userName;
		private String userEmail;
		private BigDecimal balance;
		private CardType cardType;
		private String cardNumber;
		private boolean active;
		
		public AccountBalanceResponseDtoBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

		public AccountBalanceResponseDtoBuilder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public AccountBalanceResponseDtoBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public AccountBalanceResponseDtoBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public AccountBalanceResponseDtoBuilder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public AccountBalanceResponseDtoBuilder cardType(CardType cardType) {
            this.cardType = cardType;
            return this;
        }

        public AccountBalanceResponseDtoBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public AccountBalanceResponseDtoBuilder active(boolean active) {
            this.active = active;
            return this;
        }
        public AccountBalanceResponseDto build() {
        	AccountBalanceResponseDto accountBalanAccountBalanceResponseDto = new AccountBalanceResponseDto();
        	accountBalanAccountBalanceResponseDto.accountId = this.accountId;
        	accountBalanAccountBalanceResponseDto.accountNumber = this.accountNumber;
        	accountBalanAccountBalanceResponseDto.active = this.active;
        	accountBalanAccountBalanceResponseDto.balance = this.balance;
        	accountBalanAccountBalanceResponseDto.cardNumber = this.cardNumber;
        	accountBalanAccountBalanceResponseDto.cardType = this.cardType;
        	accountBalanAccountBalanceResponseDto.userEmail = this.userEmail;
        	accountBalanAccountBalanceResponseDto.userName=this.userName;
            return accountBalanAccountBalanceResponseDto;
        }
	
}
}