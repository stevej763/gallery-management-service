package com.steve.gallery.gallerymanagementservice.adapter.rest.admin.resource;

import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.CategoryCreationRequestDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.client.CategoryDtoFactory;
import com.steve.gallery.gallerymanagementservice.domain.Category;
import com.steve.gallery.gallerymanagementservice.domain.CategoryCreationRequest;
import com.steve.gallery.gallerymanagementservice.domain.service.CategoryCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/category/admin/")
public class CategoryCreationResource {

    private final CategoryCreator categoryCreator;
    private final CategoryDtoFactory categoryDtoFactory;

    public CategoryCreationResource(CategoryCreator categoryCreator, CategoryDtoFactory categoryDtoFactory) {
        this.categoryCreator = categoryCreator;
        this.categoryDtoFactory = categoryDtoFactory;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryCreationRequestDto request) {
        CategoryCreationRequest categoryCreationRequest = new CategoryCreationRequest(request.getTitle(), request.getSubtitle());
        Category category = categoryCreator.save(categoryCreationRequest);
        CategoryDto categoryDto = categoryDtoFactory.convert(category);
        return ResponseEntity.ok(categoryDto);
    }
}
