DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS series;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS "user";

CREATE TABLE genre (
                       id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name        VARCHAR(100) NOT NULL UNIQUE,
                       description VARCHAR(255)
);

CREATE TABLE "user" (
                      id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username   VARCHAR(100) NOT NULL UNIQUE,
                      password   VARCHAR(255) NOT NULL,
                      first_name VARCHAR(100),
                      last_name  VARCHAR(100)
);

CREATE TABLE authority (
                           id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE user_authority (
                                user_id      BIGINT,
                                authority_id BIGINT,
                                PRIMARY KEY (user_id, authority_id),
                                FOREIGN KEY (user_id)      REFERENCES "user"(id),
                                FOREIGN KEY (authority_id) REFERENCES authority(id)
);

CREATE TABLE series (
                        id            BIGINT AUTO_INCREMENT PRIMARY KEY,
                        title         VARCHAR(255) NOT NULL,
                        genre_id      BIGINT,
                        total_seasons INT,
                        status        VARCHAR(20),
                        imdb_rating   DOUBLE,
                        imdb_id       VARCHAR(20),
                        FOREIGN KEY (genre_id) REFERENCES genre(id)
);

CREATE TABLE review (
                        id           BIGINT AUTO_INCREMENT PRIMARY KEY,
                        series_id    BIGINT,
                        rating       INT,
                        has_spoilers BOOLEAN,
                        text         VARCHAR(1000),
                        written_at   TIMESTAMP,
                        FOREIGN KEY (series_id) REFERENCES series(id)
);