CREATE TABLE request (
                         id bigint NOT NULL,
                         idempotent_token UUID,
                         user_id varchar(64) NOT NULL,
                         request_type smallint NOT NULL,
                         lock_value varchar(128) NOT NULL,
                         active boolean NOT NULL,
                         input_data text,
                         status varchar(128) NOT NULL,
                         status_details text,
                         created_at timestamptz,
                         updated_at timestamptz,
                         version bigint NOT NULL
);