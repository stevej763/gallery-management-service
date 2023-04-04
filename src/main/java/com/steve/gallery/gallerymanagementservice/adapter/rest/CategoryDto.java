package com.steve.gallery.gallerymanagementservice.adapter.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class CategoryDto {

    private final UUID categoryId;
    private final String title;
    private final String subtitle;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CategoryDto(@JsonProperty("categoryId") UUID categoryId,
                       @JsonProperty("title") String title,
                       @JsonProperty("subtitle") String subtitle,
                       @JsonProperty("createdAt") LocalDateTime createdAt,
                       @JsonProperty("modifiedAt") LocalDateTime modifiedAt) {
        this.categoryId = categoryId;
        this.title = title;
        this.subtitle = subtitle;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
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
