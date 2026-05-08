package hr.tvz.watchnext.watchnextapp.controller;

import hr.tvz.watchnext.watchnextapp.model.ReviewDTO;
import hr.tvz.watchnext.watchnextapp.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDTO> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/series/{seriesId}")
    public ResponseEntity<List<ReviewDTO>> findBySeriesId(@PathVariable Long seriesId) {
        return ResponseEntity.ok(reviewService.findBySeriesId(seriesId));
    }
}