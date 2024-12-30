package com.monguichet.catalog.Controller;

import com.monguichet.catalog.Entity.DTO.CategoryRequestDto;
import com.monguichet.catalog.Entity.DTO.CategoryResponseDto;
import com.monguichet.catalog.Entity.DTO.EventRequestDto;
import com.monguichet.catalog.Entity.DTO.EventResponseDto;
import com.monguichet.catalog.Service.CategoryService;
import com.monguichet.catalog.Utils.MappingProfile;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategory() {
        List<CategoryResponseDto> category = categoryService.getAllCategory();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    // Endpoint to create a new Category
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto categoryDto) {
        CategoryResponseDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }
    // Endpoint to get a category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        try {
            CategoryResponseDto category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Endpoint to update an existing Category by ID
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDto categoryDto) {
        try {
            CategoryResponseDto updatedTask = categoryService.updateCategory(id, categoryDto);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Endpoint to delete a category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
