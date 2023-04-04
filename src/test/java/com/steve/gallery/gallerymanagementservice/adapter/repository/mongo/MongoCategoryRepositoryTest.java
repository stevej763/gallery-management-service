package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Category;
import com.steve.gallery.gallerymanagementservice.domain.CategoryBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MongoCategoryRepositoryTest {

    private final CategoryDao categoryDao = mock(CategoryDao.class);
    private final CategoryFactory categoryFactory = mock(CategoryFactory.class);

    @Test
    public void canFindAllCategories() {
        MongoCategoryRepository underTest = new MongoCategoryRepository(categoryDao, categoryFactory);
        CategoryMetadata categoryMetadata = new CategoryMetadataBuilder().build();
        Category category = new CategoryBuilder().build();
        List<CategoryMetadata> categoryMetadataList = List.of(categoryMetadata);
        List<Category> categories = List.of(category);

        when(categoryDao.findAllCategories()).thenReturn(categoryMetadataList);
        when(categoryFactory.convert(categoryMetadata)).thenReturn(category);

        assertThat(underTest.findAll(), is(categories));
    }

}