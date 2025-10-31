package com.rabobank.banking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.banking.dto.request.TransferRequestDto;
import com.rabobank.banking.dto.request.WithdrawRequestDto;
import com.rabobank.banking.dto.response.TransactionResponseDto;
import com.rabobank.banking.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for handling all transaction-related API requests.
 *
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Transactions", description = "Transaction management endpoints")
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping("/withdraw")
	@Operation(summary = "Withdraw money from account", description = "Withdraws money from account using debit or credit card. Credit cards incur 1% fee.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Withdrawal successful"),
			@ApiResponse(responseCode = "400", description = "Invalid request or insufficient funds"),
			@ApiResponse(responseCode = "404", description = "Account or card not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<TransactionResponseDto> withdraw(@Valid @RequestBody WithdrawRequestDto request) {
		log.info("POST /api/transactions/withdraw - accountId: {}, amount: {}", request.getAccountId(),
				request.getAmount());
		TransactionResponseDto response = transactionService.withdraw(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/transfer")
	@Operation(summary = "Transfer money between accounts", description = "Transfers money from one account to another. Credit cards incur 1% fee on the sender.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Transfer successful"),
			@ApiResponse(responseCode = "400", description = "Invalid request or insufficient funds"),
			@ApiResponse(responseCode = "404", description = "Account or card not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<TransactionResponseDto> transfer(@Valid @RequestBody TransferRequestDto request) {
		log.info("POST /api/transactions/transfer - from: {}, to: {}, amount: {}", request.getFromAccountId(),
				request.getToAccountId(), request.getAmount());
		TransactionResponseDto response = transactionService.transfer(request);
		return ResponseEntity.ok(response);
	}
}