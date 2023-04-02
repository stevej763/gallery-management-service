package com.steve.gallery.gallerymanagementservice.adapter.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.steve.gallery.gallerymanagementservice.domain.DeletionResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class S3DeletionResource implements DeletionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3DeletionResource.class);

    private final AmazonS3 s3Client;
    private final String bucketName;

    public S3DeletionResource(AmazonS3 s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public boolean deleteFile(UUID uploadId) {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, uploadId.toString()));
        } catch (Exception e) {
            LOGGER.error("exception when deleting file from bucket={} uploadId={}", bucketName, uploadId);
            return false;
        }
        return true;
    }
}
