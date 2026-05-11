DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS series;
DROP TABLE IF EXISTS genre;

CREATE TABLE genre (
                       id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name        VARCHAR(100) NOT NULL UNIQUE,
                       description VARCHAR(255)
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