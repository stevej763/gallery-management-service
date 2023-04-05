package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class CategoryDao {

    public static final String CATEGORY_COLLECTION = "categoryMetadata";

    private final MongoTemplate mongoTemplate;

    public CategoryDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<CategoryMetadata> findAllCategories() {
        return mongoTemplate.findAll(CategoryMetadata.class, CATEGORY_COLLECTION);
    }

    public CategoryMetadata save(CategoryMetadata categoryMetadata) {
        return mongoTemplate.save(categoryMetadata, CATEGORY_COLLECTION);
    }
}
