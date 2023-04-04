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

import java.time.LocalDateTime;
import java.util.List;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryDao.CATEGORY_COLLECTION;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
public class CategoryDaoTest{

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
        CategoryMetadata category1 = createCategoryMetadata();
        CategoryMetadata category2 = createCategoryMetadata();
        CategoryMetadata category3 = createCategoryMetadata();
        mongoTemplate.save(category1, CATEGORY_COLLECTION);
        mongoTemplate.save(category2, CATEGORY_COLLECTION);
        mongoTemplate.save(category3, CATEGORY_COLLECTION);

        List<CategoryMetadata> result = underTest.findAllCategories();

        List<CategoryMetadata> expected = List.of(category1, category2, category3);
        assertThat(result, is(expected));
    }

    private CategoryMetadata createCategoryMetadata() {
        return new CategoryMetadataBuilder()
                .withCreatedAt(LocalDateTime.now().truncatedTo(SECONDS))
                .withModifiedAt(LocalDateTime.now().truncatedTo(SECONDS))
                .build();
    }
}