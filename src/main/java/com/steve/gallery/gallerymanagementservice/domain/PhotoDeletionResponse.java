package com.steve.gallery.gallerymanagementservice.domain;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class PhotoDeletionResponse {

    private final UUID photoId;
    private final boolean recordDeleted;
    private final boolean fileDeleted;

    public PhotoDeletionResponse(UUID photoId, boolean recordDeleted, boolean fileDeleted) {
        this.photoId = photoId;
        this.recordDeleted = recordDeleted;
        this.fileDeleted = fileDeleted;
    }

    public UUID getPhotoId() {
        return photoId;
    }

    public boolean isRecordDeleted() {
        return recordDeleted;
    }

    public boolean isFileDeleted() {
        return fileDeleted;
    }

    public boolean isSuccess() {
        return recordDeleted && fileDeleted;
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

    public boolean totalFailure() {
        return !recordDeleted && !fileDeleted;
    }
}
