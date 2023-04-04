package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Category;
import com.steve.gallery.gallerymanagementservice.domain.CategoryBuilder;

public class CategoryFactory {

    public Category convert(CategoryMetadata categoryMetadata) {
        return new CategoryBuilder()
                .withCategoryId(categoryMetadata.getCategoryId())
                .withTitle(categoryMetadata.getTitle())
                .withSubtitle(categoryMetadata.getSubtitle())
                .withModifiedAt(categoryMetadata.getModifiedAt())
                .withCreatedAt(categoryMetadata.getCreatedAt())
                .build();
    }
}
