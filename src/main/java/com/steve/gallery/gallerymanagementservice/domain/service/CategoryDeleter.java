package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.*;

import java.util.List;

public class CategoryDeleter {

    private final CategoryRepository categoryRepository;
    private final PhotoDetailsEditor photoDetailsEditor;

    public CategoryDeleter(CategoryRepository categoryRepository, PhotoDetailsEditor photoDetailsEditor) {
        this.categoryRepository = categoryRepository;
        this.photoDetailsEditor = photoDetailsEditor;
    }

    public CategoryDeletionResponse deleteCategory(CategoryDeletionRequest categoryDeletionRequest) {
        CategoryRecordDeletionResponse recordDeletionResponse = categoryRepository.deleteCategory(categoryDeletionRequest);
        List<Photo> updatedPhotos = photoDetailsEditor.removeCategoryFromAllPhotos(categoryDeletionRequest);
        return new CategoryDeletionResponse(
                categoryDeletionRequest.getCategoryId(),
                recordDeletionResponse.isSuccessful(),
                hasRemovedCategoryId(categoryDeletionRequest, updatedPhotos));
    }

    private boolean hasRemovedCategoryId(CategoryDeletionRequest categoryDeletionRequest, List<Photo> updatedPhotos) {
        return updatedPhotos.stream().noneMatch(photo -> photo.getPhotoId().equals(categoryDeletionRequest.getCategoryId()));
    }
}
