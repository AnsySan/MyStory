CREATE TABLE IF NOT EXISTS payments (
                                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                                        sender_account varchar(16) NOT NULL,
    receiver_account varchar(16) NOT NULL,
    currency varchar(3) NOT NULL,
    amount decimal NOT NULL,
    status varchar(11) NOT NULL,
    idempotency_key uuid ,
    scheduled_at timestamptz  DEFAULT current_timestamp,
    created_at timestamptz DEFAULT current_timestamp
    );