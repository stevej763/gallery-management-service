package com.steve.gallery.gallerymanagementservice.adapter.rest.client;

import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.CategoryDto;
import com.steve.gallery.gallerymanagementservice.domain.Category;

public class CategoryDtoFactory {
    public CategoryDto convert(Category category) {
        return new CategoryDto(category.getCategoryId(), category.getTitle(), category.getSubtitle());
    }
}
