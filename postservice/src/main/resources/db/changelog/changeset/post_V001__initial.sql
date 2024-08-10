CREATE TABLE post (
                      id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                      author_id bigint,
                      published boolean DEFAULT false NOT NULL,
                      published_at timestamptz,
                      deleted boolean DEFAULT false NOT NULL,
                      created_at timestamptz DEFAULT current_timestamp,
                      updated_at timestamptz DEFAULT current_timestamp
);

CREATE TABLE likes (
                       id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                       user_id bigint NOT NULL,
                       created_at timestamptz DEFAULT current_timestamp,
                       updated_at timestamptz DEFAULT current_timestamp

);