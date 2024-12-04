CREATE TABLE IF NOT EXISTS balance_history (
                                               id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                                               balance_id BIGINT NOT NULL,
                                               type varchar(256) NOT NULL,
    amount DECIMAL,
    created_at timestamptz DEFAULT current_timestamp,

    CONSTRAINT fk_balance_id FOREIGN KEY (balance_id) REFERENCES balances (id)
    )