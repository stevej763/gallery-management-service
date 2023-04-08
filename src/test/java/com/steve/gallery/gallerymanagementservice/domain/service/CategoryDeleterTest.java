package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.adater.CategoryRepository;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryDeletionRequest;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryDeletionResponse;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryRecordDeletionResponse;
import com.steve.gallery.gallerymanagementservice.domain.photo.Photo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.domain.photo.PhotoBuilder.aPhoto;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryDeleterTest {

    private final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    private final PhotoDetailsEditor photoDetailsEditor = mock(PhotoDetailsEditor.class);
    private final CategoryDeleter underTest = new CategoryDeleter(categoryRepository, photoDetailsEditor);

    @Test
    public void canDeleteCategory() {
        UUID categoryId = UUID.randomUUID();
        CategoryDeletionRequest categoryDeletionRequest = new CategoryDeletionRequest(categoryId);

        Photo photo = aPhoto().withCategories(List.of(categoryId)).build();
        Photo photoWithNoCategory = aPhoto().withCategories(emptyList()).build();
        when(categoryRepository.deleteCategory(categoryDeletionRequest)).thenReturn(new CategoryRecordDeletionResponse(true));
        when(photoDetailsEditor.removeCategoryFromAllPhotos(categoryDeletionRequest)).thenReturn(List.of(photoWithNoCategory));
        CategoryDeletionResponse result = underTest.deleteCategory(categoryDeletionRequest);

        CategoryDeletionResponse expected = new CategoryDeletionResponse(categoryId, true, true);
        assertThat(result, is(expected));
    }

}