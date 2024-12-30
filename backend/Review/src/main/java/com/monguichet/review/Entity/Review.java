package com.monguichet.review.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;


import java.util.Date;

@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

 //   @Column(name = "idOrder")
  //  private Long idOrder;

    @Column(name = "idEvent")
    private Long idEvent;

    @Column(name = "rating")
    @Range(min = 1, max = 5)
    private int rating;


    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

}
