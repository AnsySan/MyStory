CREATE TABLE achievement (
                             id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                             title VARCHAR(128) NOT NULL UNIQUE,
                             description VARCHAR(1024) NOT NULL UNIQUE,
                             rarity smallint NOT NULL,
                             points bigint NOT NULL,
                             created_at timestamptz DEFAULT current_timestamp,
                             updated_at timestamptz DEFAULT current_timestamp
);

CREATE TABLE user_achievement (
                                  id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                                  user_id bigint NOT NULL,
                                  achievement_id bigint NOT NULL,
                                  created_at timestamptz DEFAULT current_timestamp,
                                  updated_at timestamptz DEFAULT current_timestamp,

                                  CONSTRAINT fk_user_achievement_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE UNIQUE INDEX user_achievement_idx ON user_achievement (user_id, achievement_id);

CREATE TABLE user_achievement_progress (
                                           id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
                                           user_id bigint NOT NULL,
                                           achievement_id bigint NOT NULL,
                                           current_points bigint NOT NULL,
                                           version bigint NOT NULL DEFAULT 0,
                                           created_at timestamptz DEFAULT current_timestamp,
                                           updated_at timestamptz DEFAULT current_timestamp,

                                           CONSTRAINT fk_user_achievement_progress_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE UNIQUE INDEX user_achievement_progress_idx ON user_achievement_progress (user_id, achievement_id);