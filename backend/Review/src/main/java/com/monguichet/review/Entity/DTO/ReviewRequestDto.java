package com.monguichet.review.Entity.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {

    private Long id;
 //   private Long idOrder;
    private Long idEvent;
    private Long rating;
    private String comment;
    private Date createdAt;

}
