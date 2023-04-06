package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryFactory;
import com.steve.gallery.gallerymanagementservice.domain.category.Category;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryCreationRequest;
import com.steve.gallery.gallerymanagementservice.domain.adater.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryCreator {

    private final Logger LOGGER = LoggerFactory.getLogger(CategoryCreator.class);

    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;

    public CategoryCreator(CategoryRepository categoryRepository, CategoryFactory categoryFactory) {
        this.categoryRepository = categoryRepository;
        this.categoryFactory = categoryFactory;
    }

    public Category save(CategoryCreationRequest categoryCreationRequest) {
        Category category = categoryFactory.convert(categoryCreationRequest);
        Category savedCategory = categoryRepository.save(category);
        LOGGER.info("category creation request successful categoryId={} title={}", savedCategory.getCategoryId(), savedCategory.getTitle());
        return savedCategory;
    }
}
