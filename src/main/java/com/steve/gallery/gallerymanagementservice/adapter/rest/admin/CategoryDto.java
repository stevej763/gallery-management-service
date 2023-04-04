package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CategoryDto {
    private final UUID categoryId;
    private final String title;
    private final String subtitle;

    public CategoryDto(@JsonProperty("categoryId") UUID categoryId,
                       @JsonProperty("title") String title,
                       @JsonProperty("subtitle") String subtitle) {
        this.categoryId = categoryId;
        this.title = title;
        this.subtitle = subtitle;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
