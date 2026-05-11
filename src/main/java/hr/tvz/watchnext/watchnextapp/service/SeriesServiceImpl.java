package hr.tvz.watchnext.watchnextapp.service;

import hr.tvz.watchnext.watchnextapp.command.SeriesCommand;
import hr.tvz.watchnext.watchnextapp.enumeration.SeriesStatus;
import hr.tvz.watchnext.watchnextapp.model.Genre;
import hr.tvz.watchnext.watchnextapp.model.Series;
import hr.tvz.watchnext.watchnextapp.model.SeriesDTO;
import hr.tvz.watchnext.watchnextapp.repository.GenreRepository;
import hr.tvz.watchnext.watchnextapp.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;
    private final GenreRepository genreRepository;


    public SeriesServiceImpl(SeriesRepository seriesRepository, GenreRepository genreRepository) {
        this.seriesRepository = seriesRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<SeriesDTO> getAllSeries() {
        return seriesRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public SeriesDTO getSeriesById(Long id) {
        return seriesRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<SeriesDTO> searchByTitle(String title) {
        return seriesRepository.findByTitle(title).stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public void deleteSeries(Long id) {
        seriesRepository.deleteById(id);
    }

    @Override
    public SeriesDTO saveSeries(SeriesDTO seriesDTO) {
        Series series = convertToEntity(seriesDTO);
        Series savedSeries = seriesRepository.save(series);
        return convertToDTO(savedSeries);
    }

    @Override
    public Optional<SeriesDTO> save(SeriesCommand command) {
        boolean exists = seriesRepository.findAll().stream()
                .anyMatch(series -> series.getTitle().equals(command.getTitle()));

        if (exists) {
            return Optional.empty();
        }

        Genre genre = null;
        if (command.getGenre() != null) {
            genre = genreRepository.findByName(command.getGenre())
                    .orElseGet(() -> {
                        Genre newGenre = new Genre();
                        newGenre.setName(command.getGenre());
                        return genreRepository.save(newGenre);
                    });
        }

        Series newSeries = Series.builder()
                .title(command.getTitle())
                .genre(genre)
                .totalSeasons(command.getTotalSeasons())
                .status(command.getStatus())
                .imdbRating(command.getImdbRating())
                .imdbId(command.getImdbId())
                .build();

        Series saved = seriesRepository.save(newSeries);
        return Optional.of(convertToDTO(saved));
    }

    @Override
    public boolean delete(String title) {
        boolean exists = seriesRepository.findAll().stream()
                .anyMatch(s -> s.getTitle().equalsIgnoreCase(title));

        if (exists){
            seriesRepository.deleteByTitle(title);
            return true;
        }

        return false;
    }

    @Override
    public Optional<SeriesDTO> findByTitle(String title) {
        return seriesRepository.findAll().stream()
                .filter(s -> s.getTitle().equalsIgnoreCase(title))
                .map(this::convertToDTO)
                .findFirst();
    }

    @Override
    public Optional<SeriesDTO> findByImdbId(String imdbId) {
        return seriesRepository.findAll().stream()
                .filter(s -> imdbId.equals(s.getImdbId()))
                .map(this::convertToDTO)
                .findFirst();
    }

    private SeriesDTO convertToDTO(Series series) {
        return new SeriesDTO(
                series.getId(),
                series.getTitle(),
                series.getGenre() != null ? series.getGenre().getName() : null,
                series.getTotalSeasons(),
                series.getStatus().toString(),
                series.getImdbRating(),
                series.getImdbId());
    }

    private Series convertToEntity(SeriesDTO dto) {
        Series series = new Series();
        series.setTitle(dto.getTitle());
        if (dto.getGenreName() != null) {
            Genre genre = genreRepository.findByName(dto.getGenreName())
                    .orElseGet(() -> {
                        Genre newGenre = new Genre();
                        newGenre.setName(dto.getGenreName());
                        return genreRepository.save(newGenre);
                    });
            series.setGenre(genre);
        }        series.setTotalSeasons(dto.getTotalSeasons());
        series.setImdbRating(dto.getImdbRating());
        if (dto.getStatus() != null) {
            series.setStatus(SeriesStatus.valueOf(dto.getStatus().toUpperCase()));
        }
        return series;
    }

    @Override
    public Optional<SeriesDTO> update(String title, Series updatedSeries) {
        List<Series> existingSeriesList = seriesRepository.findByTitle(title);

        if (existingSeriesList.isEmpty()) {
            return Optional.empty();
        }

        Series seriesToUpdate = existingSeriesList.get(0);

        seriesToUpdate.setTitle(updatedSeries.getTitle());
        seriesToUpdate.setTotalSeasons(updatedSeries.getTotalSeasons());
        seriesToUpdate.setStatus(updatedSeries.getStatus());
        seriesToUpdate.setImdbRating(updatedSeries.getImdbRating());
        seriesToUpdate.setImdbId(updatedSeries.getImdbId());

        if (updatedSeries.getGenre() != null && updatedSeries.getGenre().getName() != null) {
            Genre genre = genreRepository.findByName(updatedSeries.getGenre().getName())
                    .orElseGet(() -> {
                        Genre newGenre = new Genre();
                        newGenre.setName(updatedSeries.getGenre().getName());
                        return genreRepository.save(newGenre);
                    });
            seriesToUpdate.setGenre(genre);
        }

        Series saved = seriesRepository.save(seriesToUpdate);
        return Optional.of(convertToDTO(saved));
    }

    public void listInsert(List<Series> seriesList) {
        seriesRepository.listInsert(seriesList);
    }

    public void updateStatus(Long id, SeriesStatus newStatus) {
        seriesRepository.updateStatus(id, newStatus);
    }

    public void deleteByStatus(SeriesStatus status) {
        seriesRepository.deleteByStatus(status);
    }
}
