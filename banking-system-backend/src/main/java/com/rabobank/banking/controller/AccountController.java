package com.rabobank.banking.controller;

import com.rabobank.banking.dto.response.AllAccountsBalanceResponseDto;
import com.rabobank.banking.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing bank accounts.
 *
 * Provides endpoints to view all accounts and check their
 * current balances. This controller is read-only and delegates
 *
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Accounts", description = "Account management endpoints")
public class AccountController {

	private final AccountService accountService;

	@GetMapping("/balances")
	@Operation(summary = "Get all account balances", description = "Retrieves balance information for all active accounts including user details and card information")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved account balances"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<AllAccountsBalanceResponseDto> getAllAccountBalances() {
		log.info("GET /api/accounts/balances - Fetching all account balances");
		AllAccountsBalanceResponseDto response = accountService.getAllAccountBalances();
		return ResponseEntity.ok(response);
	}
}