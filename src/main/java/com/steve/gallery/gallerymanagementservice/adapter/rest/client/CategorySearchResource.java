package com.steve.gallery.gallerymanagementservice.adapter.rest.client;

import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDto;
import com.steve.gallery.gallerymanagementservice.domain.category.Category;
import com.steve.gallery.gallerymanagementservice.domain.service.CategoryFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v1/category", produces = APPLICATION_JSON_VALUE, consumes = ALL_VALUE)
public class CategorySearchResource {

    private final Logger LOGGER = LoggerFactory.getLogger(CategorySearchResource.class);

    private final CategoryFinder categoryFinder;
    private final CategoryDtoFactory categoryDtoFactory;

    public CategorySearchResource(CategoryFinder categoryFinder, CategoryDtoFactory categoryDtoFactory) {
        this.categoryFinder = categoryFinder;
        this.categoryDtoFactory = categoryDtoFactory;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> categories() {
        List<Category> categories = categoryFinder.findAll();
        List<CategoryDto> categoryDtos = categories
                .stream()
                .map(categoryDtoFactory::convert)
                .toList();
        return ResponseEntity.ok(categoryDtos);
    }
}
