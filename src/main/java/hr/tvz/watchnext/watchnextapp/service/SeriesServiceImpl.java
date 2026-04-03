package hr.tvz.watchnext.watchnextapp.service;

import hr.tvz.watchnext.watchnextapp.command.SeriesActorCommand;
import hr.tvz.watchnext.watchnextapp.command.SeriesBasicCommand;
import hr.tvz.watchnext.watchnextapp.command.SeriesCommand;
import hr.tvz.watchnext.watchnextapp.command.SeriesRatingCommand;
import hr.tvz.watchnext.watchnextapp.enumeration.SeriesStatus;
import hr.tvz.watchnext.watchnextapp.model.Series;
import hr.tvz.watchnext.watchnextapp.model.SeriesDTO;
import hr.tvz.watchnext.watchnextapp.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;

    public SeriesServiceImpl(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @Override
    public List<SeriesDTO> getAllSeries() {
        return seriesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
                .collect(Collectors.toList());
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

        Series newSeries = Series.builder()
                .id((long) (seriesRepository.findAll().size() + 1))
                .genre(command.getGenre())
                .totalSeasons(command.getTotalSeasons())
                .status(command.getStatus())
                .imdbRating(command.getImdbRating())
                .build();

        seriesRepository.save(newSeries);

        return Optional.of(convertToDTO(newSeries));
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

    @Override
    public Optional<SeriesDTO> updateImdbRating(SeriesRatingCommand command) {
        return seriesRepository.findAll().stream()
                .filter(s -> s.getTitle().equalsIgnoreCase(command.getTitle()))
                .findFirst()
                .map(series -> {
                    series.setImdbRating(command.getImdbRating());
                    return convertToDTO(series);
                });
    }

    @Override
    public Optional<SeriesDTO> saveBasic(SeriesBasicCommand command) {
        if (seriesRepository.findAll().stream()
                .anyMatch(s -> s.getTitle().equalsIgnoreCase(command.getTitle()))) {
            return Optional.empty();
        }

        Series newSeries = Series.builder()
                .id((long) (seriesRepository.findAll().size() + 1))
                .title(command.getTitle())
                .genre(command.getGenre())
                .totalSeasons(command.getTotalSeasons())
                .status(SeriesStatus.PLANNED)
                .actors(new ArrayList<>())
                .build();

        seriesRepository.save(newSeries);
        return Optional.of(convertToDTO(newSeries));
    }

    @Override
    public boolean addActor(SeriesActorCommand command) {
        return seriesRepository.findById(command.getSeriesId())
                .map(series -> {
                    if (series.getActors() == null) {
                        series.setActors(new ArrayList<>());
                    }
                    series.getActors().add(command.getName());
                    return true;
                })
                .orElse(false);
    }


    private SeriesDTO convertToDTO(Series series) {
        return new SeriesDTO(
                series.getTitle(),
                series.getGenre(),
                series.getTotalSeasons(),
                series.getStatus().toString(),
                series.getImdbRating());
    }

    private Series convertToEntity(SeriesDTO dto) {
        Series series = new Series();
        series.setTitle(dto.getTitle());
        series.setGenre(dto.getGenre());
        series.setTotalSeasons(dto.getTotalSeasons());
        series.setImdbRating(dto.getAverageRating());
        if (dto.getStatus() != null) {
            series.setStatus(SeriesStatus.valueOf(dto.getStatus().toUpperCase()));
        }
        return series;
    }
}
