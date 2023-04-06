package com.steve.gallery.gallerymanagementservice.domain.adater;

import com.steve.gallery.gallerymanagementservice.domain.category.Category;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryDeletionRequest;
import com.steve.gallery.gallerymanagementservice.domain.category.CategoryRecordDeletionResponse;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();

    Category save(Category category);

    CategoryRecordDeletionResponse deleteCategory(CategoryDeletionRequest categoryDeletionRequest);
}
