package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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

    public boolean deleteCategory(UUID categoryId) {
        Query query = query(where(CategoryMetadata.CATEGORY_ID).is(categoryId));
        DeleteResult deleteResult = mongoTemplate.remove(query, CategoryMetadata.class, CATEGORY_COLLECTION);
        return deleteResult.getDeletedCount() == 1;
    }
}
