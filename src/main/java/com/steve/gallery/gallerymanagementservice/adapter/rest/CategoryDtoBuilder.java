package com.steve.gallery.gallerymanagementservice.adapter.rest;

import java.time.LocalDateTime;
import java.util.UUID;

public class CategoryDtoBuilder {
    private UUID categoryId = UUID.randomUUID();
    private String title;
    private String subtitle;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public static CategoryDtoBuilder aCategoryDtoBuilder() {
        return new CategoryDtoBuilder();
    }

    public CategoryDtoBuilder withCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public CategoryDtoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public CategoryDtoBuilder withSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public CategoryDtoBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CategoryDtoBuilder withModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public CategoryDto build() {
        return new CategoryDto(categoryId, title, subtitle, createdAt, modifiedAt);
    }
}