-- Insert sample users
INSERT INTO users (id, first_name, last_name, email, phone_number, created_at, updated_at) VALUES
(1, 'John', 'Doe', 'john.doe@example.com', '+31612345678', NOW(), NOW()),
(2, 'Jane', 'Smith', 'jane.smith@example.com', '+31687654321', NOW(), NOW()),
(3, 'Bob', 'Johnson', 'bob.johnson@example.com', '+31698765432', NOW(), NOW());

-- Insert sample accounts
INSERT INTO accounts (account_id, account_number, user_id, balance, active, created_at, updated_at) VALUES
('ACC001', 'NL91RABO0417164300', 1, 1500.00, true, NOW(), NOW()),
('ACC002', 'NL91RABO0417164301', 2, 2000.00, true, NOW(), NOW()),
('ACC003', 'NL91RABO0417164302', 3, 500.00, true, NOW(), NOW());

-- Insert sample cards (one debit, two credit for testing fees)
INSERT INTO cards (card_number, card_type, account_id, expiry_date, card_holder_name, active, created_at, updated_at) VALUES
('4532123456781234', 'DEBIT', 'ACC001', '2027-12-31', 'JOHN DOE', true, NOW(), NOW()),
('5412345678901234', 'CREDIT', 'ACC002', '2028-06-30', 'JANE SMITH', true, NOW(), NOW()),
('4916234567890123', 'DEBIT', 'ACC003', '2026-09-30', 'BOB JOHNSON', true, NOW(), NOW());

-- Reset sequence for users table
SELECT setval('users_id_seq', 3, true);