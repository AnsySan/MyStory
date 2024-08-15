CREATE TABLE country (
                         id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                         title varchar(64) UNIQUE NOT NULL
);

CREATE TABLE users (
                       id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                       username varchar(64) UNIQUE NOT NULL,
                       password varchar(128) NOT NULL,
                       email varchar(64) UNIQUE NOT NULL,
                       phone varchar(32) UNIQUE,
                       about_me varchar(4096),
                       active boolean DEFAULT true NOT NULL,
                       city varchar(64),
                       country_id bigint NOT NULL,
                       experience int,
                       banned boolean DEFAULT false NOT NULL,
                       created_at timestamptz DEFAULT current_timestamp,
                       updated_at timestamptz DEFAULT current_timestamp,

                       CONSTRAINT fk_country_id FOREIGN KEY (country_id) REFERENCES country (id)
);

CREATE TABLE subscription (
                              id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                              follower_id bigint NOT NULL,
                              followee_id bigint NOT NULL,
                              created_at timestamptz DEFAULT current_timestamp,
                              updated_at timestamptz DEFAULT current_timestamp,

                              CONSTRAINT fk_follower_id FOREIGN KEY (follower_id) REFERENCES users (id),
                              CONSTRAINT fk_followee_id FOREIGN KEY (followee_id) REFERENCES users (id)
);

CREATE TABLE contact (
                         id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                         user_id bigint NOT NULL,
                         contact varchar(128) NOT NULL UNIQUE,
                         type smallint NOT NULL,

                         CONSTRAINT fk_contact_owner_id FOREIGN KEY (user_id) REFERENCES users (id)
);
