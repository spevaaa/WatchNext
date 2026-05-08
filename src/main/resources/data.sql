INSERT INTO series (title, genre, total_seasons, status, imdb_rating, imdb_id)
VALUES ('Breaking Bad', 'Crime Drama', 5, 'COMPLETED', 9.5, 'tt0903747'),
       ('The Wire', 'Crime Drama', 5, 'COMPLETED', 9.3, 'tt0306414'),
       ('Dark', 'Sci-Fi', 3, 'WATCHING', 8.8, 'tt5753856'),
       ('Severance', 'Sci-Fi', 2, 'PLANNED', 8.7, 'tt11280740'),
       ('Chernobyl', 'Historical', 1, 'COMPLETED', 9.4, 'tt7366338');

INSERT INTO review (series_id, rating, has_spoilers, text, written_at)
VALUES (1, 5, false, 'Apsolutno remek-djelo, jedna od najboljih serija ikad!', NOW()),
       (1, 4, false, 'Izvrsna gluma i priča, preporučujem svima.', NOW()),
       (2, 5, true, 'Realističan prikaz Baltimore ulica, spoileri unutra!', NOW()),
       (2, 4, false, 'Kompleksni likovi i odlična scenarij.', NOW()),
       (3, 5, false, 'Najbolja europska serija, mind-blowing zaplet.', NOW()),
       (4, 4, false, 'Intrigantan koncept, jedva čekam novu sezonu.', NOW()),
       (5, 5, false, 'Potresna i edukativna, obavezno gledanje.', NOW());