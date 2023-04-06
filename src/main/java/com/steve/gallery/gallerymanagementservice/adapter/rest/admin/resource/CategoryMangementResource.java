package com.steve.gallery.gallerymanagementservice.adapter.rest.admin.resource;

import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.CategoryCreationRequestDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.CategoryDeletionResponseDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.client.CategoryDtoFactory;
import com.steve.gallery.gallerymanagementservice.domain.category.Category;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryCreationRequest;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryDeletionRequest;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryDeletionResponse;
import com.steve.gallery.gallerymanagementservice.domain.service.CategoryCreator;
import com.steve.gallery.gallerymanagementservice.domain.service.CategoryDeleter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/category/admin/")
public class CategoryMangementResource {

    private final CategoryCreator categoryCreator;
    private final CategoryDtoFactory categoryDtoFactory;
    private final CategoryDeleter categoryDeleter;

    public CategoryMangementResource(CategoryCreator categoryCreator, CategoryDtoFactory categoryDtoFactory, CategoryDeleter categoryDeleter) {
        this.categoryCreator = categoryCreator;
        this.categoryDtoFactory = categoryDtoFactory;
        this.categoryDeleter = categoryDeleter;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryCreationRequestDto request) {
        CategoryCreationRequest categoryCreationRequest = new CategoryCreationRequest(request.getTitle(), request.getSubtitle());
        Category category = categoryCreator.save(categoryCreationRequest);
        CategoryDto categoryDto = categoryDtoFactory.convert(category);
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping(value = "/delete/{categoryId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDeletionResponseDto> delete(@PathVariable("categoryId") UUID categoryId) {
        CategoryDeletionRequest categoryCreationRequest = new CategoryDeletionRequest(categoryId);
        CategoryDeletionResponse categoryDeletionResponse = categoryDeleter.deleteCategory(categoryCreationRequest);
        return ResponseEntity.ok(new CategoryDeletionResponseDto(categoryDeletionResponse.isCategoryDeleted()));
    }
}
