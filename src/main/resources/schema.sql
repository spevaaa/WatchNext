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