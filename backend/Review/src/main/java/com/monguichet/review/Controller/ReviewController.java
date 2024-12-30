package com.monguichet.review.Controller;


import com.monguichet.review.Entity.DTO.ReviewRequestDto;
import com.monguichet.review.Entity.DTO.ReviewResponseDto;
import com.monguichet.review.Exception.ReviewNotFoundException;
import com.monguichet.review.Service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // Afficher all Reviews
    @GetMapping("/")
    public ResponseEntity<List<ReviewResponseDto>> getAllReview() {
        List<ReviewResponseDto> category = reviewService.getAllReview();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // Endpoint to create a new Review
    @PostMapping("/create")
    public ResponseEntity<ReviewResponseDto> createEvent(@RequestBody ReviewRequestDto eventDto) {
        ReviewResponseDto createdReview = reviewService.createReview(eventDto);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    //Update Review
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> updateEvent(@PathVariable Long id, @RequestBody ReviewRequestDto reviewDto) {
        ReviewResponseDto updatedReview = reviewService.updateReview(id, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }
// delete Review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // Get review by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        try    {
            ReviewResponseDto review = reviewService.getReviewById(id);
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        catch (ReviewNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review with id "+id+" not found");
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> getReviewsByEventId(@RequestParam Long idEvent) {
        try {
            List<ReviewResponseDto> reviews = reviewService.getReviewsByEventId(idEvent);
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews found for event with ID: " + idEvent);
        }
    }
 //
 // Get average rating by event ID
 @GetMapping("/average-rating")
 public ResponseEntity<?> getAverageRatingByEventId(@RequestParam Long idEvent) {
     try {
         double averageRating = reviewService.getAverageRatingByEventId(idEvent);
         return ResponseEntity.ok(averageRating);
     } catch (ReviewNotFoundException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews found for event with ID: " + idEvent);
     }
 }
    //get Total Number Of Reviews By Event Id
    @GetMapping("/total/{idEvent}")
    public ResponseEntity<?> getTotalNumberOfReviewsByEventId(@PathVariable Long idEvent) {
        try {
            long totalReviews = reviewService.getTotalNumberOfReviewsByEventId(idEvent);
        return ResponseEntity.ok(totalReviews);
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reviews found for event with ID: " + idEvent);
        }
    }

 // delete All Reviews By EventId
    @DeleteMapping("/event/{eventId}")
    public ResponseEntity<String> deleteReviewsByEventId(@PathVariable Long eventId) {
        try {
            reviewService.deleteReviewsByEventId(eventId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }




}
