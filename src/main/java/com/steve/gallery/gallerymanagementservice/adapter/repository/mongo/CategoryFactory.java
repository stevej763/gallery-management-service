package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Category;
import com.steve.gallery.gallerymanagementservice.domain.CategoryBuilder;
import com.steve.gallery.gallerymanagementservice.domain.CategoryCreationRequest;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public Category convert(CategoryCreationRequest categoryCreationRequest) {
        return new CategoryBuilder()
                .withCategoryId(UUID.randomUUID())
                .withTitle(categoryCreationRequest.getTitle())
                .withSubtitle(categoryCreationRequest.getSubtitle())
                .withModifiedAt(LocalDateTime.now())
                .withCreatedAt(LocalDateTime.now())
                .build();
    }
}
