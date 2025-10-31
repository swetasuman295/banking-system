-- Create cards table
CREATE TABLE IF NOT EXISTS cards (
    id BIGSERIAL PRIMARY KEY,
    card_number VARCHAR(16) NOT NULL UNIQUE,
    card_type VARCHAR(10) NOT NULL,
    account_id VARCHAR(20) NOT NULL UNIQUE,
    expiry_date DATE NOT NULL,
    card_holder_name VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_cards_account FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    CONSTRAINT chk_card_type CHECK (card_type IN ('DEBIT', 'CREDIT'))
);

-- Create indexes
CREATE INDEX idx_cards_card_number ON cards(card_number);
CREATE INDEX idx_cards_account_id ON cards(account_id);