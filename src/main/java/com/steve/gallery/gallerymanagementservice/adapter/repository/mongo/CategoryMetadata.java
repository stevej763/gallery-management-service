package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class CategoryMetadata {

    @Id
    private final UUID categoryId;

    private final String title;
    private final String subtitle;

    public CategoryMetadata(
            @JsonProperty("categoryId") UUID categoryId,
            @JsonProperty("title") String title,
            @JsonProperty("subtitle") String subtitle) {
        this.categoryId = categoryId;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
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
