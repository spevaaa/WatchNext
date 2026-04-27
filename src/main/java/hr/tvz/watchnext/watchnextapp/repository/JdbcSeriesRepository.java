package hr.tvz.watchnext.watchnextapp.repository;

import hr.tvz.watchnext.watchnextapp.enumeration.SeriesStatus;
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

    private final RowMapper<Series> rowMapper = (rs, rowNum) -> Series.builder()
            .id(rs.getLong("id"))
            .title(rs.getString("title"))
            .genre(rs.getString("genre"))
            .totalSeasons(rs.getInt("total_seasons"))
            .status(SeriesStatus.valueOf(rs.getString("status")))
            .imdbRating(rs.getDouble("imdb_rating"))
            .imdbId(rs.getString("imdb_id"))
            .build();

    public JdbcSeriesRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.insert = new SimpleJdbcInsert(jdbc)
                .withTableName("series")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Series> findAll() {
        return jdbc.query("SELECT * FROM series", rowMapper);
    }

    @Override
    public Optional<Series> findById(Long id) {
        return jdbc.query("SELECT * FROM series WHERE id = ?", rowMapper, id)
                .stream().findFirst();
    }

    @Override
    public List<Series> findByTitle(String val) {
        String pattern = "%" + val.toLowerCase() + "%";
        return jdbc.query("SELECT * FROM series WHERE LOWER(title) LIKE ?", rowMapper, pattern);
    }

    @Override
    public Series save(Series series) {
        if (series.getId() != null) {
            jdbc.update(
                    "UPDATE series SET title=?, genre=?, total_seasons=?, status=?, imdb_rating=?, imdb_id=? WHERE id=?",
                    series.getTitle(),
                    series.getGenre(),
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
            params.put("genre",         series.getGenre());
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
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM series WHERE id = ?", id);
    }

    @Override
    public void deleteByTitle(String title) {
        jdbc.update("DELETE FROM series WHERE LOWER(title) = LOWER(?)", title);
    }

    @Override
    public void listInsert(List<Series> seriesList) {
        String sql = "INSERT INTO series (title, genre, total_seasons, status, imdb_rating, imdb_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbc.batchUpdate(sql, seriesList, seriesList.size(), (ps, series) -> {
            ps.setString(1, series.getTitle());
            ps.setString(2, series.getGenre());
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