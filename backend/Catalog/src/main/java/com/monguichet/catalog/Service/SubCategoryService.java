package com.monguichet.catalog.Service;


import com.monguichet.catalog.Entity.DTO.SubCategoryRequestDto;
import com.monguichet.catalog.Entity.DTO.SubCategoryResponseDto;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryResponseDto> getAllSubCategory();

    SubCategoryResponseDto createSubCategory(SubCategoryRequestDto subcategoryDto);

    SubCategoryResponseDto updateSubCategory(Long id, SubCategoryRequestDto subcategoryDto) throws Exception;
    void deleteSubCategory(Long id) throws Exception;

    List<SubCategoryResponseDto> getSubCategoriesByCategoryId(Long categoryId);

    SubCategoryResponseDto getSubCategoryNameById(Long id);
}