package com.monguichet.catalog.Controller;

import com.monguichet.catalog.Entity.DTO.CategoryRequestDto;
import com.monguichet.catalog.Entity.DTO.CategoryResponseDto;
import com.monguichet.catalog.Entity.DTO.SubCategoryRequestDto;
import com.monguichet.catalog.Entity.DTO.SubCategoryResponseDto;
import com.monguichet.catalog.Service.CategoryService;
import com.monguichet.catalog.Service.SubCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/category/subcategory")
@AllArgsConstructor
public class SubCategoryController {

    private final SubCategoryService subCategoryService;
// Get all Subcategory
    @GetMapping("/")
    public ResponseEntity<List<SubCategoryResponseDto>> getAllSubCategory() {
        List<SubCategoryResponseDto> subCategory = subCategoryService.getAllSubCategory();
        return new ResponseEntity<>(subCategory, HttpStatus.OK);
    }
    ///////////////////////
// get subcategori name by id
    @GetMapping("/{id}") // Modification de l'annotation pour éviter le conflit
    public ResponseEntity<SubCategoryResponseDto> getSubCategoryById(@PathVariable Long id) {
        try {
            SubCategoryResponseDto subCategory = subCategoryService.getSubCategoryNameById(id);
            return new ResponseEntity<>(subCategory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Endpoint to get subcategories by category ID
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<SubCategoryResponseDto>> getSubCategoriesByCategoryId(@PathVariable Long categoryId) {
        List<SubCategoryResponseDto> subCategories = subCategoryService.getSubCategoriesByCategoryId(categoryId);
        if (subCategories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }


    // Endpoint to create a new SubCategory
    @PostMapping("/create")
    public ResponseEntity<SubCategoryResponseDto> createSubCategory(@RequestBody SubCategoryRequestDto subcategoryDto) {
        SubCategoryResponseDto createdSubCategory = subCategoryService.createSubCategory(subcategoryDto);
        return new ResponseEntity<>(createdSubCategory, HttpStatus.CREATED);
    }
    // Endpoint to update a SubCategory
    @PutMapping("/{id}")
    public ResponseEntity<SubCategoryResponseDto> updateSubCategory(@PathVariable Long id, @RequestBody SubCategoryRequestDto subcategoryDto) {
        try {
            SubCategoryResponseDto updatedSubCat = subCategoryService.updateSubCategory(id, subcategoryDto);
            return new ResponseEntity<>(updatedSubCat, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete a Sub category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        try {
            subCategoryService.deleteSubCategory(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
