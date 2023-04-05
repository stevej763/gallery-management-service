package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Category;
import com.steve.gallery.gallerymanagementservice.domain.CategoryRepository;

import java.util.List;

public class MongoCategoryRepository implements CategoryRepository {

    private final CategoryDao categoryDao;
    private final CategoryFactory categoryFactory;
    private final CategoryMetadataFactory categoryMetadataFactory;

    public MongoCategoryRepository(
            CategoryDao categoryDao,
            CategoryFactory categoryFactory,
            CategoryMetadataFactory categoryMetadataFactory) {
        this.categoryDao = categoryDao;
        this.categoryFactory = categoryFactory;
        this.categoryMetadataFactory = categoryMetadataFactory;
    }

    @Override
    public List<Category> findAll() {
        List<CategoryMetadata> categoryMetadata = categoryDao.findAllCategories();
        return categoryMetadata.stream().map(categoryFactory::convert).toList();
    }

    @Override
    public Category save(Category category) {
        CategoryMetadata categoryMetadata = categoryMetadataFactory.convert(category);
        CategoryMetadata savedCategory = categoryDao.save(categoryMetadata);
        return categoryFactory.convert(savedCategory);
    }
}
