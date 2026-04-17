package hr.tvz.watchnext.watchnextapp.model;

import hr.tvz.watchnext.watchnextapp.enumeration.SeriesStatus;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Series {

    private Long id;
    private String title;
    private String genre;
    private int totalSeasons;
    private SeriesStatus status;
    private Double imdbRating;
    private String imdbId;
    private List<String> actors;

}
