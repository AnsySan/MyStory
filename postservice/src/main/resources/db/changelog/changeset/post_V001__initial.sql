CREATE TABLE post (
                      id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                      author_id bigint,
                      published boolean DEFAULT false NOT NULL,
                      published_at timestamptz,
                      deleted boolean DEFAULT false NOT NULL,
                      created_at timestamptz DEFAULT current_timestamp,
                      updated_at timestamptz DEFAULT current_timestamp,
                      verified boolean NOT NULL
);

CREATE TABLE comment (
                         id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                         content varchar(4096) NOT NULL,
                         author_id bigint NOT NULL,
                         post_id bigint NOT NULL,
                         created_at timestamptz DEFAULT current_timestamp,
                         updated_at timestamptz DEFAULT current_timestamp,

                         CONSTRAINT fk_post_id FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE
);

CREATE TABLE likes (
                       id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                       post_id bigint,
                       comment_id bigint,
                       user_id bigint NOT NULL,
                       created_at timestamptz DEFAULT current_timestamp,
                       updated_at timestamptz DEFAULT current_timestamp,

                       CONSTRAINT fk_post_id FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE CASCADE,
                       CONSTRAINT fk_comment_id FOREIGN KEY (comment_id) REFERENCES comment (id) ON DELETE CASCADE
);

CREATE TABLE resource (
                          id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY  UNIQUE,
                          key VARCHAR NOT NULL,
                          size bigint NOT NULL,
                          created_at timestamptz DEFAULT current_timestamp,
                          name VARCHAR(64) NOT NULL,
                          type VARCHAR(64) NOT NULL,
                          post_id bigint,

                          CONSTRAINT fk_post_id FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
);
