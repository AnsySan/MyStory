CREATE TABLE IF NOT EXISTS tariff (
                                      id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                                      tariff_type VARCHAR(32) NOT NULL,
    current_rate FLOAT8,
    rate_history VARCHAR(2048) DEFAULT '[]',
    created_at TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS savings_account (
                                               id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                                               account_id BIGINT NOT NULL ,
                                               balance DECIMAL(19, 2) NOT NULL,
    tariff_history TEXT,
    current_tariff BIGINT REFERENCES tariff (id),
    last_interest_date TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT current_timestamp,
    updated_at TIMESTAMPTZ DEFAULT current_timestamp,
    version BIGINT NOT NULL,
    CONSTRAINT fk_account_saving FOREIGN KEY (account_id) REFERENCES account (id)
    );