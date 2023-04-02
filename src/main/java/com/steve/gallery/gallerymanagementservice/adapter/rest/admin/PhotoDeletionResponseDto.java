package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class PhotoDeletionResponseDto {

    private final boolean success;
    private final String error;

    public PhotoDeletionResponseDto(boolean success) {
        this(success, null);
    }

    public PhotoDeletionResponseDto(@JsonProperty("success") boolean success, @JsonProperty("error") String error) {
        this.success = success;
        this.error = error;
    }

    public static PhotoDeletionResponseDto fileDeletionFailed() {
        return new PhotoDeletionResponseDto(false, "file deletion failed");
    }

    public static PhotoDeletionResponseDto recordDeletionFailed() {
        return new PhotoDeletionResponseDto(false, "record deletion failed");
    }

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
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
