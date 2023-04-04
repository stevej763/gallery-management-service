package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Category;

public class CategoryFactory {

    public Category convert(CategoryMetadata categoryMetadata) {
        return new Category(categoryMetadata.getCategoryId(),
                            categoryMetadata.getTitle(),
                            categoryMetadata.getSubtitle());
    }
}
