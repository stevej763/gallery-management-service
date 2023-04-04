package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.Category;
import com.steve.gallery.gallerymanagementservice.domain.CategoryBuilder;
import com.steve.gallery.gallerymanagementservice.domain.CategoryRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryFinderServiceTest {

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final CategoryFinder underTest = new CategoryFinder(categoryRepository);

    @Test
    public void shouldReturnAllCategories() {
        UUID categoryId = UUID.randomUUID();
        Category category = new CategoryBuilder()
                .withCategoryId(categoryId)
                .build();
        when(categoryRepository.findAll()).thenReturn(List.of(category));


        assertThat(underTest.findAll(), is(List.of(category)));
    }

}