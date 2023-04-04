package com.steve.gallery.gallerymanagementservice.adapter.rest.client;

import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDtoBuilder;
import com.steve.gallery.gallerymanagementservice.domain.Category;

public class CategoryDtoFactory {
    public CategoryDto convert(Category category) {
        return new CategoryDtoBuilder()
                .withCategoryId(category.getCategoryId())
                .withTitle(category.getTitle())
                .withSubtitle(category.getSubtitle())
                .withModifiedAt(category.getModifiedAt())
                .withCreatedAt(category.getCreatedAt())
                .build();
    }
}
