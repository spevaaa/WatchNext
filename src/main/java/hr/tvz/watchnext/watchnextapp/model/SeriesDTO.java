package hr.tvz.watchnext.watchnextapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeriesDTO {

    private String title;
    private String genre;
    private Integer totalSeasons;
    private String status;
    private Double imdbRating;
    private String imdbId;

    public SeriesDTO() {}

    public SeriesDTO(String title, String genre, Integer totalSeasons, String status, Double imdbRating, String imdbId) {
        this.title = title;
        this.genre = genre;
        this.totalSeasons = totalSeasons;
        this.status = status;
        this.imdbRating = imdbRating;
        this.imdbId = imdbId;
    }
}
