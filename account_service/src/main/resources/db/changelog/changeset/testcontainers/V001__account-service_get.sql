CREATE TABLE IF NOT EXISTS account
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    number     VARCHAR(20) CHECK (LENGTH(number) >= 12),
    user_id    BIGINT,
    project_id BIGINT,
    type       VARCHAR(128) NOT NULL,
    currency   VARCHAR(3)   NOT NULL,
    status     VARCHAR(32)  NOT NULL,
    created_at timestamptz           DEFAULT current_timestamp,
    updated_at timestamptz           DEFAULT current_timestamp,
    closed_at  timestamptz,
    version    BIGINT       NOT NULL DEFAULT 0,

    CONSTRAINT account_owner_check CHECK (
(user_id IS NULL AND project_id IS NOT NULL)
    OR
(user_id IS NOT NULL AND project_id IS NULL)
    )
    );

INSERT INTO account(number, user_id, project_id, type, currency, status)
VALUES ('123456789013123', 1, null, 'CURRENT_ACCOUNT', 'USD', 'ACTIVE');