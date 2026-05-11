package hr.tvz.watchnext.watchnextapp.repository;

import hr.tvz.watchnext.watchnextapp.enumeration.SeriesStatus;
import hr.tvz.watchnext.watchnextapp.model.Genre;
import hr.tvz.watchnext.watchnextapp.model.Series;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class JdbcSeriesRepository implements SeriesRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert insert;

    private final RowMapper<Series> rowMapper = (rs, rowNum) -> {
        Genre genre = new Genre();
        genre.setId(rs.getLong("genre_id"));
        genre.setName(rs.getString("genre_name"));

        return Series.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .genre(genre)
                .totalSeasons(rs.getInt("total_seasons"))
                .status(SeriesStatus.valueOf(rs.getString("status")))
                .imdbRating(rs.getDouble("imdb_rating"))
                .imdbId(rs.getString("imdb_id"))
                .build();
    };

    public JdbcSeriesRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.insert = new SimpleJdbcInsert(jdbc)
                .withTableName("series")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Series> findAll() {
        return jdbc.query(
                "SELECT s.*, g.id as genre_id, g.name as genre_name " +
                        "FROM series s LEFT JOIN genre g ON s.genre_id = g.id",
                rowMapper
        );
    }

    @Override
    public Optional<Series> findById(Long id) {
        return jdbc.query(
                "SELECT s.*, g.id as genre_id, g.name as genre_name " +
                        "FROM series s LEFT JOIN genre g ON s.genre_id = g.id WHERE s.id = ?",
                rowMapper, id
        ).stream().findFirst();
    }

    @Override
    public List<Series> findByTitle(String val) {
        String pattern = "%" + val.toLowerCase() + "%";
        return jdbc.query(
                "SELECT s.*, g.id as genre_id, g.name as genre_name " +
                        "FROM series s LEFT JOIN genre g ON s.genre_id = g.id WHERE LOWER(s.title) LIKE ?",
                rowMapper, pattern
        );
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Series save(Series series) {
        if (series.getId() != null) {
            jdbc.update(
                    "UPDATE series SET title=?, genre_id=?, total_seasons=?, status=?, imdb_rating=?, imdb_id=? WHERE id=?",
                    series.getTitle(),
                    series.getGenre() != null ? series.getGenre().getId() : null,
                    series.getTotalSeasons(),
                    series.getStatus().name(),
                    series.getImdbRating(),
                    series.getImdbId(),
                    series.getId()
            );
            return series;
        } else {
            Map<String, Object> params = new HashMap<>();
            params.put("title",         series.getTitle());
            params.put("genre_id",      series.getGenre() != null ? series.getGenre().getId() : null);
            params.put("total_seasons", series.getTotalSeasons());
            params.put("status",        series.getStatus().name());
            params.put("imdb_rating",   series.getImdbRating());
            params.put("imdb_id",       series.getImdbId());

            Number generatedId = insert.executeAndReturnKey(params);
            series.setId(generatedId.longValue());
            return series;
        }
    }

    @Override
    public void deleteByTitle(String title) {

    }

    @Override
    public void listInsert(List<Series> seriesList) {
        String sql = "INSERT INTO series (title, genre_id, total_seasons, status, imdb_rating, imdb_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbc.batchUpdate(sql, seriesList, seriesList.size(), (ps, series) -> {
            ps.setString(1, series.getTitle());
            ps.setObject(2, series.getGenre() != null ? series.getGenre().getId() : null);
            ps.setInt(3, series.getTotalSeasons());
            ps.setString(4, series.getStatus().name());
            ps.setDouble(5, series.getImdbRating());
            ps.setString(6, series.getImdbId());
        });
    }

    @Override
    public void updateStatus(Long id, SeriesStatus newStatus) {
        jdbc.update("UPDATE series SET status = ? WHERE id = ?", newStatus.name(), id);
    }

    @Override
    public void deleteByStatus(SeriesStatus status) {
        jdbc.update("DELETE FROM series WHERE status = ?", status.name());
    }
}