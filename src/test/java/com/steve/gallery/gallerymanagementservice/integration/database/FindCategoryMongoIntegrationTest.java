package com.steve.gallery.gallerymanagementservice.integration.database;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.*;
import com.steve.gallery.gallerymanagementservice.domain.category.Category;
import com.steve.gallery.gallerymanagementservice.domain.service.CategoryFinder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.domain.category.CategoryBuilder.aCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FindCategoryMongoIntegrationTest extends BaseMongoIntegrationTest {

    @Test
    public void canFindAllCategories() {
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();
        UUID categoryId3 = UUID.randomUUID();
        Category category1 = createCategory(categoryId1);
        Category category2 = createCategory(categoryId2);
        Category category3 = createCategory(categoryId3);
        CategoryMetadata categoryMetadata1 = createCategoryMetadata(categoryId1);
        CategoryMetadata categoryMetadata2 = createCategoryMetadata(categoryId2);
        CategoryMetadata categoryMetadata3 = createCategoryMetadata(categoryId3);

        saveCategoryToDatabase(categoryMetadata1);
        saveCategoryToDatabase(categoryMetadata2);
        saveCategoryToDatabase(categoryMetadata3);

        CategoryDao categoryDao = new CategoryDao(mongoTemplate);
        CategoryFactory categoryFactory = new CategoryFactory();
        MongoCategoryRepository categoryRepository = new MongoCategoryRepository(categoryDao, categoryFactory, new CategoryMetadataFactory());
        CategoryFinder categoryFinder = new CategoryFinder(categoryRepository);

        List<Category> allCategories = categoryFinder.findAll();

        assertThat(allCategories, is(List.of(category1, category2, category3)));

    }

    private Category createCategory(UUID categoryId) {
        return aCategory()
                .withCategoryId(categoryId)
                .withTitle("my category")
                .withSubtitle("a nice description of the category")
                .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    private CategoryMetadata createCategoryMetadata(UUID categoryId) {
        return new CategoryMetadataBuilder()
                .withCategoryId(categoryId)
                .withTitle("my category")
                .withSubtitle("a nice description of the category")
                .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
    }
}
