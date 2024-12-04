CREATE INDEX idempotent_key
    ON request(idempotent_token);

CREATE UNIQUE INDEX lock_request ON request(lock_value)
    WHERE active = true;

CREATE INDEX userId
    ON request(user_id)