package com.steve.gallery.gallerymanagementservice.adapter.s3;

public class S3FileUploadException extends RuntimeException {

    public S3FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
