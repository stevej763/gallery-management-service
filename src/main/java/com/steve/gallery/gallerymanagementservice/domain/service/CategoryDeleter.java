package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CategoryDeleter {

    private final Logger LOGGER = LoggerFactory.getLogger(CategoryDeleter.class);

    private final CategoryRepository categoryRepository;
    private final PhotoDetailsEditor photoDetailsEditor;

    public CategoryDeleter(CategoryRepository categoryRepository, PhotoDetailsEditor photoDetailsEditor) {
        this.categoryRepository = categoryRepository;
        this.photoDetailsEditor = photoDetailsEditor;
    }

    public CategoryDeletionResponse deleteCategory(CategoryDeletionRequest categoryDeletionRequest) {
        CategoryRecordDeletionResponse recordDeletionResponse = categoryRepository.deleteCategory(categoryDeletionRequest);
        List<Photo> updatedPhotos = photoDetailsEditor.removeCategoryFromAllPhotos(categoryDeletionRequest);
        boolean categoryRemovedFromPhotos = hasRemovedCategoryId(categoryDeletionRequest, updatedPhotos);
        LOGGER.info("category deletion request complete success={} photosProcessed={} allRemoved={}",
                    recordDeletionResponse.isSuccessful(), updatedPhotos.size(), categoryRemovedFromPhotos);
        return new CategoryDeletionResponse(
                categoryDeletionRequest.getCategoryId(),
                recordDeletionResponse.isSuccessful(),
                categoryRemovedFromPhotos);
    }

    private boolean hasRemovedCategoryId(CategoryDeletionRequest categoryDeletionRequest, List<Photo> updatedPhotos) {
        return updatedPhotos.stream().noneMatch(photo -> photo.getPhotoId().equals(categoryDeletionRequest.getCategoryId()));
    }
}
