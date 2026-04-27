package hr.tvz.watchnext.watchnextapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeriesDTO {

    private Long id;
    private String title;
    private String genre;
    private Integer totalSeasons;
    private String status;
    private Double imdbRating;
    private String imdbId;

    public SeriesDTO() {}

    public SeriesDTO(Long id, String title, String genre, Integer totalSeasons, String status, Double imdbRating, String imdbId) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.totalSeasons = totalSeasons;
        this.status = status;
        this.imdbRating = imdbRating;
        this.imdbId = imdbId;
    }
}
