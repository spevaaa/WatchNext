package hr.tvz.watchnext.watchnextapp.service;

import hr.tvz.watchnext.watchnextapp.command.SeriesCommand;
import hr.tvz.watchnext.watchnextapp.model.Series;
import hr.tvz.watchnext.watchnextapp.model.SeriesDTO;

import java.util.List;
import java.util.Optional;

public interface SeriesService {
    List<SeriesDTO> getAllSeries();
    SeriesDTO getSeriesById(Long id);
    List<SeriesDTO> searchByTitle(String title);
    void deleteSeries(Long id);
    SeriesDTO saveSeries(SeriesDTO seriesDTO);
    Optional<SeriesDTO> save(SeriesCommand command);
    boolean delete(String title);
    public Optional<SeriesDTO> findByTitle(String title);
    public Optional<SeriesDTO> findByImdbId(String imdbId);
    public Optional<SeriesDTO> update(String title, Series updatedSeries);

}
