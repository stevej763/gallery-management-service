package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Category;
import com.steve.gallery.gallerymanagementservice.domain.CategoryRepository;

import java.util.List;

public class MongoCategoryRepository implements CategoryRepository {

    private final CategoryDao categoryDao;
    private final CategoryFactory categoryFactory;

    public MongoCategoryRepository(CategoryDao categoryDao, CategoryFactory categoryFactory) {
        this.categoryDao = categoryDao;
        this.categoryFactory = categoryFactory;
    }

    @Override
    public List<Category> findAll() {
        List<CategoryMetadata> categoryMetadata = categoryDao.findAllCategories();
        return categoryMetadata.stream().map(categoryFactory::convert).toList();
    }
}
