package hr.tvz.watchnext.watchnextapp.service;

import hr.tvz.watchnext.watchnextapp.model.Review;
import hr.tvz.watchnext.watchnextapp.model.ReviewDTO;
import hr.tvz.watchnext.watchnextapp.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDTO> findBySeriesId(Long seriesId) {
        return reviewRepository.findBySeriesId(seriesId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ReviewDTO> findAll() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getSeries().getId(),
                review.getSeries().getTitle(),
                review.getRating(),
                review.isHasSpoilers(),
                review.getText(),
                review.getWrittenAt()
        );
    }
}