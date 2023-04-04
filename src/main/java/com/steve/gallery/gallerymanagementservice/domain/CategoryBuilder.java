package com.steve.gallery.gallerymanagementservice.domain;

import java.util.UUID;

public class CategoryBuilder {

    private UUID categoryId = UUID.randomUUID();
    private String title;
    private String subtitle;

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

    public Category build() {
        return new Category(categoryId, title, subtitle);
    }

}