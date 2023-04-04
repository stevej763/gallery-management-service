package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.dao;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryDao;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryMetadata;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryMetadataBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryDao.CATEGORY_COLLECTION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
public class CategoryDaoTest{

    private static final UUID CATEGORY_ID = UUID.randomUUID();

    @Autowired
    MongoTemplate mongoTemplate;

    private CategoryDao underTest;

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
    }

    @BeforeEach
    void setUp() {
        underTest = new CategoryDao(mongoTemplate);
    }

    @Test
    public void retrievesAllPhotosFromPhotosCollection() {
        CategoryMetadata category1 = new CategoryMetadataBuilder().build();
        CategoryMetadata category2 = new CategoryMetadataBuilder().build();
        CategoryMetadata category3 = new CategoryMetadataBuilder().build();
        mongoTemplate.save(category1, CATEGORY_COLLECTION);
        mongoTemplate.save(category2, CATEGORY_COLLECTION);
        mongoTemplate.save(category3, CATEGORY_COLLECTION);

        List<CategoryMetadata> result = underTest.findAllCategories();

        List<CategoryMetadata> expected = List.of(category1, category2, category3);
        assertThat(result, is(expected));
    }
}