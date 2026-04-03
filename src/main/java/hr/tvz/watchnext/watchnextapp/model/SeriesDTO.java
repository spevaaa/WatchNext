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
    private Double averageRating;

    public SeriesDTO() {
    }

    public SeriesDTO(String title, String genre, Integer totalSeasons, String status, Double averageRating) {
        this.title = title;
        this.genre = genre;
        this.totalSeasons = totalSeasons;
        this.status = status;
        this.averageRating = averageRating;
    }
}
