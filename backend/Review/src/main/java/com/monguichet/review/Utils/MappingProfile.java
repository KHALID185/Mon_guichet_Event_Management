package com.monguichet.review.Utils;


import com.monguichet.review.Entity.DTO.ReviewRequestDto;
import com.monguichet.review.Entity.DTO.ReviewResponseDto;
import com.monguichet.review.Entity.Review;
import org.modelmapper.ModelMapper;


public class MappingProfile {
    private static final ModelMapper modelMapper = new ModelMapper();

    /******************Review**********************************/
    public static Review mapToEntity(ReviewRequestDto reviewDto) {
        return modelMapper.map(reviewDto, Review.class);
    }

    public static ReviewResponseDto mapToDto(Review event) {
        return modelMapper.map(event, ReviewResponseDto.class);
    }



}
