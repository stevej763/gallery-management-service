package com.steve.gallery.gallerymanagementservice.domain.category;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class CategoryDeletionResponse {

    private final UUID categoryId;
    private final boolean categoryDeleted;
    private final boolean categoryRemovedFromPhotos;

    public CategoryDeletionResponse(UUID categoryId, boolean categoryDeleted, boolean categoryRemovedFromPhotos) {
        this.categoryId = categoryId;
        this.categoryDeleted = categoryDeleted;
        this.categoryRemovedFromPhotos = categoryRemovedFromPhotos;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public boolean isCategoryDeleted() {
        return categoryDeleted;
    }

    public boolean isCategoryRemovedFromPhotos() {
        return categoryRemovedFromPhotos;
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
