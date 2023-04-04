package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import java.util.UUID;

public class CategoryMetadataBuilder {

    private UUID categoryId = UUID.randomUUID();
    private String title;
    private String subtitle;

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

    public CategoryMetadata build() {
        return new CategoryMetadata(categoryId, title, subtitle);
    }
}