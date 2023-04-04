package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import java.util.UUID;

public class CategoryDtoBuilder {
    private UUID categoryId;
    private String title;
    private String subtitle;

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

    public CategoryDto build() {
        return new CategoryDto(categoryId, title, subtitle);
    }
}