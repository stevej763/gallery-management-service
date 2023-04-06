package com.steve.gallery.gallerymanagementservice.domain.category;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class CategoryRequest {

    private final UUID photoId;
    private final UUID categoryId;

    public CategoryRequest(UUID photoId, UUID categoryId) {
        this.photoId = photoId;
        this.categoryId = categoryId;
    }

    public UUID getPhotoId() {
        return photoId;
    }

    public UUID getCategoryId() {
        return categoryId;
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
