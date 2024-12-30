package com.monguichet.catalog.Service;

import com.monguichet.catalog.Entity.DTO.CategoryRequestDto;
import com.monguichet.catalog.Entity.DTO.CategoryResponseDto;
import com.monguichet.catalog.Entity.DTO.EventResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategory();

    CategoryResponseDto getCategoryById(Long id)throws Exception;

    CategoryResponseDto createCategory(CategoryRequestDto categoryDto);
    CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryDto) throws Exception;

    // Delete Category by Id and all his SubCategories
    void deleteCategory(Long id) throws Exception;
}
