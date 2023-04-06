package com.steve.gallery.gallerymanagementservice.domain.category;

import java.time.LocalDateTime;
import java.util.UUID;

public class CategoryBuilder {

    private UUID categoryId = UUID.randomUUID();
    private String title;
    private String subtitle;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public static CategoryBuilder aCategory() {
        return new CategoryBuilder();
    }

    public CategoryBuilder withCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public CategoryBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public CategoryBuilder withSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public CategoryBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CategoryBuilder withModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public Category build() {
        return new Category(categoryId, title, subtitle, createdAt, modifiedAt);
    }
}