package com.rabobank.banking.controller;

import com.rabobank.banking.domain.exception.*;
import com.rabobank.banking.dto.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles all exceptions across the application in a centralized manner.
 *
 * Converts exceptions into meaningful HTTP responses with proper
 *
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * Handles Insufficient fund exception and returns a structured error response
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<ErrorResponseDto> handleInsufficientFunds(InsufficientFundsException ex, WebRequest request) {

		log.warn("Insufficient funds: {}", ex.getMessage());

		Map<String, Object> details = new HashMap<>();
		details.put("accountId", ex.getAccountId());
		details.put("availableBalance", ex.getAvailableBalance());
		details.put("requestedAmount", ex.getRequestedAmount());

		ErrorResponseDto error = ErrorResponseDto.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.message(ex.getMessage()).path(request.getDescription(false).replace("uri=", ""))
				.errorCode(ex.getErrorCode()).details(details).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * Handles Accountnotfoundexception
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleAccountNotFound(AccountNotFoundException ex, WebRequest request) {

		log.warn("Account not found: {}", ex.getMessage());

		ErrorResponseDto error = ErrorResponseDto.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value()).error(HttpStatus.NOT_FOUND.getReasonPhrase())
				.message(ex.getMessage()).path(request.getDescription(false).replace("uri=", ""))
				.errorCode(ex.getErrorCode()).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	/**
	 * Handles InvalidCardException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(InvalidCardException.class)
	public ResponseEntity<ErrorResponseDto> handleInvalidCard(InvalidCardException ex, WebRequest request) {

		log.warn("Invalid card: {}", ex.getMessage());

		ErrorResponseDto error = ErrorResponseDto.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.message(ex.getMessage()).path(request.getDescription(false).replace("uri=", ""))
				.errorCode(ex.getErrorCode()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * Handles InvalidTransactionException.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(InvalidTransactionException.class)
	public ResponseEntity<ErrorResponseDto> handleInvalidTransaction(InvalidTransactionException ex,
			WebRequest request) {

		log.warn("Invalid transaction: {}", ex.getMessage());

		ErrorResponseDto error = ErrorResponseDto.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.message(ex.getMessage()).path(request.getDescription(false).replace("uri=", ""))
				.errorCode(ex.getErrorCode()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * Handles validation errors for @valid annotated request bodies 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex,
			WebRequest request) {

		log.warn("Validation failed: {}", ex.getMessage());

		Map<String, Object> validationErrors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			validationErrors.put(fieldName, errorMessage);
		});

		ErrorResponseDto error = ErrorResponseDto.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.message("Validation failed").path(request.getDescription(false).replace("uri=", ""))
				.errorCode("VALIDATION_ERROR").details(validationErrors).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * Handles Generic exceptions
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex, WebRequest request) {

		log.error("Unexpected error occurred", ex);

		ErrorResponseDto error = ErrorResponseDto.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).message("An unexpected error occurred")
				.path(request.getDescription(false).replace("uri=", "")).errorCode("INTERNAL_ERROR").build();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}