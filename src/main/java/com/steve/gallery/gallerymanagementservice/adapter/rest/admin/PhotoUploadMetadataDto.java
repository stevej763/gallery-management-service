package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class PhotoUploadMetadataDto {

    private final String title;
    private final String description;
    private final List<String> tags;
    private final List<String> categories;

    public PhotoUploadMetadataDto(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("tags") List<String> tags,
            @JsonProperty("categories") List<String> categories) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getCategories() {
        return categories;
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
