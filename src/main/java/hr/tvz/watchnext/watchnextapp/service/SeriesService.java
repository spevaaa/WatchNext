package hr.tvz.watchnext.watchnextapp.service;

import hr.tvz.watchnext.watchnextapp.command.SeriesCommand;
<<<<<<< HEAD
=======
import hr.tvz.watchnext.watchnextapp.command.SeriesRatingCommand;
>>>>>>> a018e0761070cfc270de17c89d024e120a715b1c
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
<<<<<<< HEAD
=======
    Optional<SeriesDTO> saveBasic(SeriesBasicCommand command);
    boolean addActor(SeriesActorCommand command);
    public Optional<SeriesDTO> updateImdbRating(SeriesRatingCommand command);
>>>>>>> a018e0761070cfc270de17c89d024e120a715b1c
    public Optional<SeriesDTO> update(String title, Series updatedSeries);

}
