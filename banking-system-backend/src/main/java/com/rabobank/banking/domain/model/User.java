package com.rabobank.banking.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

/**
 * User entity representing a bank customer.
 *
 * @author Sweta Rabobank Assignment
 * @version 1.0.0
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@Setter(AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "First name is required")
	private String firstName;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "Last name is required")
	private String lastName;

	@Column(nullable = false, unique = true, length = 100)
	@Email(message = "Valid email is required")
	@NotBlank(message = "Email is required")
	private String email;

	@Column(nullable = false, length = 20)
	@NotBlank(message = "Phone number is required")
	private String phoneNumber;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	
	public String getFullName() {
		return firstName + " " + lastName;
	}
}