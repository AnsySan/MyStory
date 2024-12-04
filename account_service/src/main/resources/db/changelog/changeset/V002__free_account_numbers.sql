CREATE TABLE IF NOT EXISTS free_account_numbers
(
    id             bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_type   smallint                                         NOT NULL,
    account_number VARCHAR(20) CHECK (LENGTH(account_number) >= 12) NOT NULL,
    CONSTRAINT uc_account_type_account_number UNIQUE (account_type, account_number)
    );
CREATE UNIQUE INDEX account_type_number_idx ON free_account_numbers (account_type, account_number);

CREATE TABLE IF NOT EXISTS account_numbers_sequence
(
    id            bigint PRIMARY CREATE TABLE IF NOT EXISTS free_account_numbers
    (
    id             bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_type   smallint                                         NOT NULL,
    account_number VARCHAR(20) CHECK (LENGTH(account_number) >= 12) NOT NULL,
    CONSTRAINT uc_account_type_account_number UNIQUE (account_type, account_number)
    );
    CREATE UNIQUE INDEX account_type_number_idx ON free_account_numbers (account_type, account_number);

    CREATE TABLE IF NOT EXISTS account_numbers_sequence
(
    id            bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_type  smallint NOT NULL,
    current_count BIGINT   NOT NULL DEFAULT 0
);
    CREATE UNIQUE INDEX account_numbers_sequence_ind ON account_numbers_sequence (account_type, current_count);

    INSERT INTO account_numbers_sequence (account_type, current_count)
    VALUES (0, 0), -- CURRENT_ACCOUNT
(1, 0), -- SAVINGS_ACCOUNT
(2, 0), -- CORPORATE_ACCOUNT
(3, 0), -- PERSONAL_ACCOUNT
(4, 0), -- JOINT_ACCOUNT
(5, 0), -- FOREIGN_CURRENCY_ACCOUNT
(6, 0), -- INVESTMENT_ACCOUNT
(7, 0), -- MERCHANT_ACCOUNT
(8, 0), -- TRUST_ACCOUNT
(9, 0); -- ESCROW_ACCOUNTKEY GENERATED ALWAYS AS IDENTITY,
    account_type  smallint NOT NULL,
    current_count BIGINT   NOT NULL DEFAULT 0
);
CREATE UNIQUE INDEX account_numbers_sequence_ind ON account_numbers_sequence (account_type, current_count);

INSERT INTO account_numbers_sequence (account_type, current_count)
VALUES (0, 0), -- CURRENT_ACCOUNT
       (1, 0), -- SAVINGS_ACCOUNT
       (2, 0), -- CORPORATE_ACCOUNT
       (3, 0), -- PERSONAL_ACCOUNT
       (4, 0), -- JOINT_ACCOUNT
       (5, 0), -- FOREIGN_CURRENCY_ACCOUNT
       (6, 0), -- INVESTMENT_ACCOUNT
       (7, 0), -- MERCHANT_ACCOUNT
       (8, 0), -- TRUST_ACCOUNT
       (9, 0); -- ESCROW_ACCOUNT