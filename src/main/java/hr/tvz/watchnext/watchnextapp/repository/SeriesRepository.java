package hr.tvz.watchnext.watchnextapp.repository;

import hr.tvz.watchnext.watchnextapp.model.Series;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository {
    List<Series> findAll();
    Optional<Series> findById(Long id);
    List<Series> findByTitle(String val);
    void deleteById(Long id);
    Series save(Series series);
    void deleteByTitle(String title);
}
