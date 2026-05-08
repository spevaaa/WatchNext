package hr.tvz.watchnext.watchnextapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "has_spoilers")
    private boolean hasSpoilers;

    @Column(name = "text", length = 1000)
    private String text;

    @Column(name = "written_at")
    private LocalDateTime writtenAt;
}