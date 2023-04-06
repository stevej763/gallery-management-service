package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDtoBuilder;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.resource.CategoryMangementResource;
import com.steve.gallery.gallerymanagementservice.adapter.rest.client.CategoryDtoFactory;
import com.steve.gallery.gallerymanagementservice.domain.category.Category;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryCreationRequest;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryDeletionRequest;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryDeletionResponse;
import com.steve.gallery.gallerymanagementservice.domain.service.CategoryCreator;
import com.steve.gallery.gallerymanagementservice.domain.service.CategoryDeleter;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.domain.category.CategoryBuilder.aCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryMangementResourceTest {

    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    private final CategoryDtoFactory categoryDtoFactory = mock(CategoryDtoFactory.class);
    private final CategoryCreator categoryCreator = mock(CategoryCreator.class);
    private final CategoryDeleter categoryDeleter = mock(CategoryDeleter.class);
    private final CategoryMangementResource underTest = new CategoryMangementResource(categoryCreator, categoryDtoFactory, categoryDeleter);

    @Test
    public void canCreateNewCategory() {
        Category category = aCategory().build();
        CategoryDto categoryDto = new CategoryDtoBuilder().build();
        when(categoryCreator.save(new CategoryCreationRequest(TITLE, SUBTITLE))).thenReturn(category);
        when(categoryDtoFactory.convert(category)).thenReturn(categoryDto);

        ResponseEntity<CategoryDto> result = underTest.create(new CategoryCreationRequestDto(TITLE, SUBTITLE));
        assertThat(result, is(ResponseEntity.ok(categoryDto)));
    }

    @Test
    public void shouldHandleCategoryDeletionRequest() {
        UUID categoryId = UUID.randomUUID();

        CategoryDeletionRequest categoryDeletionRequest = new CategoryDeletionRequest(categoryId);
        CategoryDeletionResponse categoryDeletionResponse = new CategoryDeletionResponse(categoryId, true, true);
        when(categoryDeleter.deleteCategory(categoryDeletionRequest)).thenReturn(categoryDeletionResponse);
        ResponseEntity<CategoryDeletionResponseDto> result = underTest.delete(categoryId);

        CategoryDeletionResponseDto response = new CategoryDeletionResponseDto(true);
        assertThat(result, is(ResponseEntity.ok(response)));
    }

}