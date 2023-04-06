package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryFactory;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryMetadata;
import com.steve.gallery.gallerymanagementservice.domain.category.Category;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryBuilder;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryCreationRequest;
import com.steve.gallery.gallerymanagementservice.domain.adater.CategoryRepository;
import org.junit.jupiter.api.Test;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryMetadataBuilder.aCategoryMetadata;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryCreatorTest {

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final CategoryFactory categoryFactory = mock(CategoryFactory.class);

    @Test
    public void canCreateACategory() {
        CategoryCreator underTest = new CategoryCreator(categoryRepository, categoryFactory);

        CategoryCreationRequest categoryCreationRequest = new CategoryCreationRequest("title", "subtitle");
        Category category = new CategoryBuilder().build();
        CategoryMetadata categoryMetadata = aCategoryMetadata().build();
        when(categoryFactory.convert(categoryCreationRequest)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryFactory.convert(categoryMetadata)).thenReturn(category);

        Category result = underTest.save(categoryCreationRequest);

        assertThat(result, is(category));
    }

}