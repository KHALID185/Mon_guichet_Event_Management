package com.monguichet.catalog.Service;

import com.monguichet.catalog.Entity.DTO.CategoryRequestDto;
import com.monguichet.catalog.Entity.DTO.CategoryResponseDto;
import com.monguichet.catalog.Entity.Event;
import com.monguichet.catalog.Entity.SubCategory;
import com.monguichet.catalog.Repository.CategoryRepo;
import com.monguichet.catalog.Repository.EventRepo;
import com.monguichet.catalog.Repository.SubCategoryRepo;
import com.monguichet.catalog.Utils.MappingProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final SubCategoryRepo subCategoryRepo;
    private final EventRepo eventRepo;

    @Override
    public List<CategoryResponseDto> getAllCategory() {
        return categoryRepo.findAll().stream()
                .map(MappingProfile::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) throws Exception {
        var task = categoryRepo.findById(id).orElseThrow(() -> new Exception("Category not found"));
        return MappingProfile.mapToDto(task);
    }
    /*Create Category*/
    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryDto) {
        var category = MappingProfile.mapToEntity(categoryDto);
        return MappingProfile.mapToDto(categoryRepo.save(category));
    }
    // Endpoint to Update a category by ID
    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto categoryDto) throws Exception {
        var category = categoryRepo.findById(id).orElseThrow(() -> new Exception("Category not found"));
        category.setName(categoryDto.getName());

        return MappingProfile.mapToDto(categoryRepo.save(category));
    }
    // Delete Category by Id and all his SubCategories
    @Override
    public void deleteCategory(Long id) throws Exception {
        var category = categoryRepo.findById(id).orElseThrow(() -> new Exception("Category not found"));

        // Find all subcategories associated with the category
        List<SubCategory> subCategories = subCategoryRepo.findByCategoryId(id);

        // Delete all events associated with each subcategory
        for (SubCategory subCategory : subCategories) {
            List<Event> events = eventRepo.findBySubCategoryId(subCategory.getId());
            eventRepo.deleteAll(events);
        }

        // Delete all subcategories associated with the category
        subCategoryRepo.deleteAll(subCategories);

        // Delete the category itself
        categoryRepo.delete(category);
    }

}
