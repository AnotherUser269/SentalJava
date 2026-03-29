CREATE TYPE transfer_status AS ENUM ('done', 'error');

CREATE TABLE IF NOT EXISTS transactions (
    id VARCHAR(255) PRIMARY KEY,
    from_account_id BIGINT NOT NULL,
    to_account_id BIGINT NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    status transfer_status NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT PRIMARY KEY,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00
);