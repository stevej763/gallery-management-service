package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.category.Category;

public class CategoryMetadataFactory {
    public CategoryMetadata convert(Category category) {
        return new CategoryMetadata(
                category.getCategoryId(),
                category.getTitle(),
                category.getSubtitle(),
                category.getCreatedAt(),
                category.getModifiedAt());
    }
}
