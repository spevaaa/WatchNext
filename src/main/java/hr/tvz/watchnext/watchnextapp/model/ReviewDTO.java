package hr.tvz.watchnext.watchnextapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long seriesId;
    private String seriesTitle;
    private int rating;
    private boolean hasSpoilers;
    private String text;
    private LocalDateTime writtenAt;
}