package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import java.time.LocalDateTime;
import java.util.UUID;

public class CategoryMetadataBuilder {

    private UUID categoryId = UUID.randomUUID();
    private String title;
    private String subtitle;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public static CategoryMetadataBuilder aCategoryMetadata() {
        return new CategoryMetadataBuilder();
    }

    public CategoryMetadataBuilder withCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public CategoryMetadataBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public CategoryMetadataBuilder withSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public CategoryMetadataBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CategoryMetadataBuilder withModifiedAt(LocalDateTime modifiedA) {
        this.modifiedAt = modifiedA;
        return this;
    }

    public CategoryMetadata build() {
        return new CategoryMetadata(categoryId, title, subtitle, createdAt, modifiedAt);
    }
}