package hr.tvz.watchnext.watchnextapp.repository;

import hr.tvz.watchnext.watchnextapp.enumeration.SeriesStatus;
import hr.tvz.watchnext.watchnextapp.model.Series;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MockSeriesRepository implements SeriesRepository {

    private final List<Series> seriesList = new ArrayList<>();

    public MockSeriesRepository() {

        Series s1 = new Series();
        s1.setId(1L);
        s1.setTitle("Breaking Bad");
        s1.setGenre("Crime");
        s1.setTotalSeasons(5);
        s1.setStatus(SeriesStatus.COMPLETED);
        s1.setImdbRating(9.5);
        s1.setImdbId("tt6748933");

        Series s2 = new Series();
        s2.setId(2L);
        s2.setTitle("Stranger Things");
        s2.setGenre("Fantasy");
        s2.setTotalSeasons(5);
        s2.setStatus(SeriesStatus.WATCHING);
        s2.setImdbRating(8.7);
        s2.setImdbId("tt0903747");

        seriesList.add(s1);
        seriesList.add(s2);
    }

    @Override
    public List<Series> findAll() {
        return seriesList;
    }

    @Override
    public Optional<Series> findById(Long id) {
        return seriesList.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    @Override
    public List<Series> findByTitle(String val) {
        return seriesList.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(val.toLowerCase()))
                .toList();
    }

    public void deleteById(Long id) {
        seriesList.removeIf(s -> s.getId().equals(id));
    }

    @Override
    public Series save(Series series) {
        if (series.getId() != null) {
            boolean exists = seriesList.stream().anyMatch(s -> s.getId().equals(series.getId()));
            if (exists) deleteById(series.getId());

            seriesList.add(series);
        } else {
            Long nextId = seriesList.stream()
                    .mapToLong(s -> s.getId() != null ? s.getId() : 0L)
                    .max()
                    .orElse(0L) + 1;
            series.setId(nextId);
            seriesList.add(series);
        }
        if (series.getId() == null) {
            Long nextId = seriesList.stream()
                    .mapToLong(Series::getId)
                    .max()
                    .orElse(0L) + 1;
            series.setId(nextId);
        }
        seriesList.add(series);
        return series;
    }

    @Override
    public void deleteByTitle(String title) {
        if (title == null) return;
        seriesList.removeIf(s -> s.getTitle() != null && s.getTitle().equalsIgnoreCase(title));
    }


}
