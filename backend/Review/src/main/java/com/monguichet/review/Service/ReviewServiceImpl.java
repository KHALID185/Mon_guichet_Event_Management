package com.monguichet.review.Service;

import com.monguichet.review.Entity.DTO.ReviewRequestDto;
import com.monguichet.review.Entity.DTO.ReviewResponseDto;
import com.monguichet.review.Entity.Review;
import com.monguichet.review.Exception.ReviewNotFoundException;
import com.monguichet.review.Repository.ReviewRepo;
import com.monguichet.review.Utils.MappingProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;


    // Afficher All Reviews
    @Override
    public List<ReviewResponseDto> getAllReview() {
        //   Pageable pageable = PageRequest(0, 5, Sort.by())
        return reviewRepo.findAll().stream()
                .map(MappingProfile::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponseDto createReview(ReviewRequestDto reviewDto) {
        // Map the EventRequestDto to Event entity
        var review = MappingProfile.mapToEntity(reviewDto);
        // Save the event
        Review savedReview = reviewRepo.save(review);
        // Map the saved Event entity to EventResponseDto and return
        return MappingProfile.mapToDto(savedReview);
    }
    //Update Review
    @Override
    public ReviewResponseDto updateReview(Long id, ReviewRequestDto reviewDto) {
        Review reviewToUpdate = reviewRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Update the review attributes
        reviewToUpdate.setRating(Math.toIntExact(reviewDto.getRating()));
        reviewToUpdate.setComment(reviewDto.getComment());

        Review updatedReview = reviewRepo.save(reviewToUpdate);
        return MappingProfile.mapToDto(updatedReview);
    }
    // Delete Review by Id
    @Override
    public void deleteReviewById(Long id) {
        reviewRepo.deleteById(id);
    }

    @Override
    public ReviewResponseDto getReviewById(Long id){
        var Review = reviewRepo.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id "+id));
        return MappingProfile.mapToDto(Review);
    }

    @Override
    public List<ReviewResponseDto> getReviewsByEventId(Long idEvent) {
        // Trouver les revues par ID d'événement
        List<Review> reviews = reviewRepo.findAllByidEvent(idEvent);

        // Vérifier si des revues ont été trouvées
        if (reviews.isEmpty()) {
            // Si aucune revue n'est trouvée, lever une exception
            throw new ReviewNotFoundException("No reviews found for event with ID: " + idEvent);
        }

        // Mapper les revues trouvées en DTO de réponse
        return reviews.stream()
                .map(MappingProfile::mapToDto)
                .collect(Collectors.toList());
    }
///get Average Rating By Event Id
    @Override
    public double getAverageRatingByEventId(Long idEvent) {
        List<Review> reviews = reviewRepo.findAllByidEvent(idEvent);
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException(null);
        }

        // Calculate the average rating
        double sum = reviews.stream().mapToInt(Review::getRating).sum();
        return sum / reviews.size();
    }
    // get Total Number Of Reviews By Event Id
    @Override
    public long getTotalNumberOfReviewsByEventId(Long idEvent) {
        List<Review> reviews = reviewRepo.findAllByidEvent(idEvent);
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException(null);
        }
        return (long) reviews.size();
    }
// delete Reviews By Event Id
    @Override
    public void deleteReviewsByEventId(Long eventId) throws ReviewNotFoundException {
        List<Review> reviews = reviewRepo.findAllByidEvent(eventId);
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException("No reviews found for event with ID: " + eventId);
        }
        reviewRepo.deleteAll(reviews);
    }



}
