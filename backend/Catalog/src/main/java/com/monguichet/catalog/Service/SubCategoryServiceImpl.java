package com.monguichet.catalog.Service;

import com.monguichet.catalog.Entity.DTO.SubCategoryRequestDto;
import com.monguichet.catalog.Entity.DTO.SubCategoryResponseDto;
import com.monguichet.catalog.Entity.Event;
import com.monguichet.catalog.Entity.SubCategory;
import com.monguichet.catalog.Repository.EventRepo;
import com.monguichet.catalog.Repository.SubCategoryRepo;
import com.monguichet.catalog.Utils.MappingProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService{
    private final SubCategoryRepo subCategoryRepo;
    private final EventRepo eventRepo;

    @Override
    public List<SubCategoryResponseDto> getAllSubCategory() {
        return subCategoryRepo.findAll().stream()
                .map(MappingProfile::mapToDto)
                .collect(Collectors.toList());
    }
    // New method to create sub category
    @Override
    public SubCategoryResponseDto createSubCategory(SubCategoryRequestDto subcategoryDto) {
        var subcategory = MappingProfile.mapToEntity(subcategoryDto);
        return MappingProfile.mapToDto(subCategoryRepo.save(subcategory));
    }

    @Override
    public SubCategoryResponseDto updateSubCategory(Long id, SubCategoryRequestDto subcategoryDto) throws Exception {
        var category = subCategoryRepo.findById(id).orElseThrow(() -> new Exception("Sub Category not found"));
        category.setName(subcategoryDto.getName());

        return MappingProfile.mapToDto(subCategoryRepo.save(category));
    }

    @Override
    public void deleteSubCategory(Long id) throws Exception {
        var subcategory = subCategoryRepo.findById(id).orElseThrow(() -> new Exception("Sub Category not found"));

        // Retrieve events associated with the subcategory
        List<Event> events = eventRepo.findBySubCategoryId(id);

        // Delete each event associated with the subcategory
        for (Event event : events) {
            eventRepo.delete(event);
        }
        subCategoryRepo.delete(subcategory);
    }

    @Override
    public List<SubCategoryResponseDto> getSubCategoriesByCategoryId(Long categoryId) {
        List<SubCategory> subCategories = subCategoryRepo.findByCategoryId(categoryId);
        return subCategories.stream()
                .map(MappingProfile::mapToDto)
                .collect(Collectors.toList());
    }


    @Override
    public SubCategoryResponseDto getSubCategoryNameById(Long id) {
        SubCategory subCategory = subCategoryRepo.findById(id).orElse(null);
        if (subCategory != null) {
            SubCategoryResponseDto responseDto = new SubCategoryResponseDto();
            responseDto.setId(subCategory.getId());
            responseDto.setName(subCategory.getName());
            return responseDto;
        }
        return null;
    }


}
