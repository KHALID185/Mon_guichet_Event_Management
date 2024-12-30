package com.monguichet.review.Service;

import com.monguichet.review.Entity.DTO.ReviewRequestDto;
import com.monguichet.review.Entity.DTO.ReviewResponseDto;
import com.monguichet.review.Exception.ReviewNotFoundException;

import java.util.List;

public interface ReviewService {

    List<ReviewResponseDto> getAllReview();
    ReviewResponseDto createReview(ReviewRequestDto eventDto);

    //Update Review
    ReviewResponseDto updateReview(Long id, ReviewRequestDto reviewDto);
    void deleteReviewById(Long id);
    ReviewResponseDto getReviewById(Long id) throws ReviewNotFoundException;
    List<ReviewResponseDto> getReviewsByEventId(Long idEvent) throws ReviewNotFoundException;
    double getAverageRatingByEventId(Long idEvent) throws ReviewNotFoundException;
    void deleteReviewsByEventId(Long eventId) throws ReviewNotFoundException;
    long getTotalNumberOfReviewsByEventId(Long idEvent);
}
