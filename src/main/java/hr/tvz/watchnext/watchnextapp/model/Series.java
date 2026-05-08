package hr.tvz.watchnext.watchnextapp.model;

import hr.tvz.watchnext.watchnextapp.enumeration.SeriesStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="series")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "total_seasons")
    private int totalSeasons;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SeriesStatus status;

    @Column(name = "imdb_rating")
    private Double imdbRating;

    @Column(name = "imdb_id")
    private String imdbId;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
    private List<Review> reviews;

}
