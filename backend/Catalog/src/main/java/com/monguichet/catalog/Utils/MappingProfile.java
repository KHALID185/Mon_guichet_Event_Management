package com.monguichet.catalog.Utils;

import com.monguichet.catalog.Entity.Category;
import com.monguichet.catalog.Entity.DTO.*;
import com.monguichet.catalog.Entity.Event;
import com.monguichet.catalog.Entity.SubCategory;
import org.modelmapper.ModelMapper;


public class MappingProfile {
    private static final ModelMapper modelMapper = new ModelMapper();

    /******************Event**********************************/
    public static Event mapToEntity(EventRequestDto eventDto) {
        return modelMapper.map(eventDto, Event.class);
    }

    public static EventResponseDto mapToDto(Event event) {
        return modelMapper.map(event, EventResponseDto.class);
    }

/******************Category**********************************/
public static Category mapToEntity(CategoryRequestDto categoryDto) {
    return modelMapper.map(categoryDto, Category.class);
}

    public static CategoryResponseDto mapToDto(Category category) {
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    /******************SubCategory**********************************/
    public static SubCategory mapToEntity(SubCategoryRequestDto subcategoryDto) {
        return modelMapper.map(subcategoryDto, SubCategory.class);
    }

    public static SubCategoryResponseDto mapToDto(SubCategory subCategory) {
        return modelMapper.map(subCategory, SubCategoryResponseDto.class);
    }



}
