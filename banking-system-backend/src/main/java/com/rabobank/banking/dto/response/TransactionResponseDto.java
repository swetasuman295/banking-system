package com.rabobank.banking.dto.response;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.rabobank.banking.domain.model.CardType;
import com.rabobank.banking.domain.model.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

/**
 * Data transfer DTO for transaction response details after processing.
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Value
@Builder
@Schema(description = "Transaction result information")
public final class TransactionResponseDto {

	@Schema(description = "Transaction ID", example = "TXN-20251026-001")
	private String transactionId;

	@Schema(description = "Source account ID", example = "ACC001")
	private String accountId;

	@Schema(description = "Destination account ID (for transfers)", example = "ACC002")
	private String toAccountId;

	@Schema(description = "Transaction type", example = "WITHDRAWAL")
	private Transaction.TransactionType type;

	@Schema(description = "Transaction amount", example = "100.00")
	private BigDecimal amount;

	@Schema(description = "Transaction fee", example = "1.00")
	private BigDecimal fee;

	@Schema(description = "Total amount charged", example = "101.00")
	private BigDecimal totalAmount;

	@Schema(description = "Card type used", example = "CREDIT")
	private CardType cardType;

	@Schema(description = "Balance before transaction", example = "1500.00")
	private BigDecimal balanceBefore;

	@Schema(description = "Balance after transaction", example = "1399.00")
	private BigDecimal balanceAfter;

	@Schema(description = "Transaction description", example = "ATM Withdrawal")
	private String description;

	@Schema(description = "Transaction status", example = "SUCCESS")
	private Transaction.TransactionStatus status;

	@Schema(description = "Transaction timestamp", example = "2025-10-26T10:30:00")
	private LocalDateTime timestamp;
}