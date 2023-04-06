package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.category.Category;
import com.steve.gallery.gallerymanagementservice.domain.adater.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CategoryFinder {

    private final Logger LOGGER = LoggerFactory.getLogger(CategoryFinder.class);

    private final CategoryRepository categoryRepository;

    public CategoryFinder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
        LOGGER.info("search for all categories made totalCategories={}", categories.size());
        return categories;
    }
}
