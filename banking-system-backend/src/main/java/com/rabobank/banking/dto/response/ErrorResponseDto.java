package com.rabobank.banking.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Error response DTO for API error handling.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response structure")
public class ErrorResponseDto {

	@Schema(description = "Timestamp of error", example = "2025-10-26T10:30:00")
	private LocalDateTime timestamp;

	@Schema(description = "HTTP status code", example = "400")
	private int status;

	@Schema(description = "HTTP status text", example = "Bad Request")
	private String error;

	@Schema(description = "Error message", example = "Insufficient funds")
	private String message;

	@Schema(description = "Request path", example = "/api/transactions/withdraw")
	private String path;

	@Schema(description = "Application error code", example = "INSUFFICIENT_FUNDS")
	private String errorCode;

	@Schema(description = "Additional error details")
	private Map<String, Object> details;
}
