package hr.tvz.watchnext.watchnextapp.repository;

import hr.tvz.watchnext.watchnextapp.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesJpaRepository extends JpaRepository<Series, Long> {
    List<Series> findByTitle(String title);
    List<Series> findByGenre(String genre);
}