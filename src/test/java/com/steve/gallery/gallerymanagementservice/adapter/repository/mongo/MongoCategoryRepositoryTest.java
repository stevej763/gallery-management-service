package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.category.Category;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryDeletionRequest;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryRecordDeletionResponse;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryMetadataBuilder.aCategoryMetadata;
import static com.steve.gallery.gallerymanagementservice.domain.category.CategoryBuilder.aCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MongoCategoryRepositoryTest {

    private final CategoryDao categoryDao = mock(CategoryDao.class);
    private final CategoryFactory categoryFactory = mock(CategoryFactory.class);
    private final CategoryMetadataFactory categoryMetadataFactory = mock(CategoryMetadataFactory.class);

    private final MongoCategoryRepository underTest = new MongoCategoryRepository(
            categoryDao,
            categoryFactory,
            categoryMetadataFactory);

    @Test
    public void canFindAllCategories() {
        Category category = aCategory().build();
        CategoryMetadata categoryMetadata = aCategoryMetadata().build();
        List<CategoryMetadata> categoryMetadataList = List.of(categoryMetadata);
        List<Category> categories = List.of(category);

        when(categoryDao.findAllCategories()).thenReturn(categoryMetadataList);
        when(categoryFactory.convert(categoryMetadata)).thenReturn(category);

        assertThat(underTest.findAll(), is(categories));
    }

    @Test
    public void canCreateCategory() {
        Category category = aCategory().build();
        CategoryMetadata categoryMetadata = aCategoryMetadata().build();
        when(categoryMetadataFactory.convert(category)).thenReturn(categoryMetadata);
        when(categoryDao.save(categoryMetadata)).thenReturn(categoryMetadata);
        when(categoryFactory.convert(categoryMetadata)).thenReturn(category);

        assertThat(underTest.save(category), is(category));
    }

    @Test
    public void canDeleteCategory() {
        UUID categoryId = UUID.randomUUID();
        when(categoryDao.deleteCategory(categoryId)).thenReturn(true);

        CategoryDeletionRequest request = new CategoryDeletionRequest(categoryId);

        CategoryRecordDeletionResponse result = underTest.deleteCategory(request);
        assertThat(result, is(new CategoryRecordDeletionResponse(true)));
    }
}