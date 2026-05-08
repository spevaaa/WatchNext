DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS series;

CREATE TABLE series (
                        id            INT PRIMARY KEY AUTO_INCREMENT,
                        title         VARCHAR(255) NOT NULL,
                        genre         VARCHAR(100),
                        total_seasons INT,
                        status        VARCHAR(20),
                        imdb_rating   DOUBLE,
                        imdb_id       VARCHAR(20)
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