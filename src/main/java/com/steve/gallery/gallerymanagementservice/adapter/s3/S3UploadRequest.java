package com.steve.gallery.gallerymanagementservice.adapter.s3;

import com.amazonaws.services.s3.model.PutObjectRequest;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

public class S3UploadRequest {

    private final PutObjectRequest putObjectRequest;
    private final UUID uploadId;

    public S3UploadRequest(PutObjectRequest putObjectRequest, UUID uploadId) {
        this.putObjectRequest = putObjectRequest;
        this.uploadId = uploadId;
    }

    public String getUploadIdAsString() {
        return uploadId.toString();
    }

    public UUID getUploadId() {
        return uploadId;
    }

    public PutObjectRequest getPutObjectRequest() {
        return putObjectRequest;
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
