CREATE TABLE IF NOT EXISTS balances (
                                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                                        account_id BIGINT NOT NULL,
                                        auth_balance DECIMAL,
                                        actual_balance DECIMAL,
                                        created_at timestamptz DEFAULT current_timestamp,
                                        updated_at timestamptz DEFAULT current_timestamp,
                                        version BIGINT NOT NULL DEFAULT 0,

                                        CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account (id)
    )