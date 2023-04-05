package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryFactory;
import com.steve.gallery.gallerymanagementservice.domain.Category;
import com.steve.gallery.gallerymanagementservice.domain.CategoryCreationRequest;
import com.steve.gallery.gallerymanagementservice.domain.CategoryRepository;

public class CategoryCreator {

    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;

    public CategoryCreator(CategoryRepository categoryRepository, CategoryFactory categoryFactory) {
        this.categoryRepository = categoryRepository;
        this.categoryFactory = categoryFactory;
    }

    public Category save(CategoryCreationRequest categoryCreationRequest) {
        Category category = categoryFactory.convert(categoryCreationRequest);
        return categoryRepository.save(category);
    }
}
