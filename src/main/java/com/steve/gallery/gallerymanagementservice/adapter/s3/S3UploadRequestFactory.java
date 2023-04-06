package com.steve.gallery.gallerymanagementservice.adapter.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoUploadRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

public class S3UploadRequestFactory {
    public S3UploadRequest create(String bucketName, PhotoUploadRequest photoUploadRequest) throws FileNotFoundException {
        File photo = photoUploadRequest.getPhoto();
        UUID uploadId = UUID.randomUUID();
        ObjectMetadata metadata = createObjectMetadata(photo);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadId.toString(), new FileInputStream(photo), metadata);
        return new S3UploadRequest(putObjectRequest, uploadId);
    }

    private static ObjectMetadata createObjectMetadata(File photo) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(photo.length());
        metadata.setContentType("image/jpeg");
        return metadata;
    }
}
