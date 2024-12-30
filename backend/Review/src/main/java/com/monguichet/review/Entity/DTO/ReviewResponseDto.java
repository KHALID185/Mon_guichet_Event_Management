package com.monguichet.review.Entity.DTO;


import com.monguichet.review.Entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;
 //   private Long idOrder;
    private Long idEvent;
    private Long rating;
    private String comment;
    private Date createdAt;
    // Constructor to map Review entity to ReviewResponseDto
    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.idEvent = review.getIdEvent();
        this.rating = (long) review.getRating();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
    }


    public ReviewResponseDto(String message) {
    }
}
