package com.steve.gallery.gallerymanagementservice.adapter.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.UploadResource;
import com.steve.gallery.gallerymanagementservice.domain.UploadedPhoto;
import com.steve.gallery.gallerymanagementservice.domain.UploadedPhotoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.UUID;

public class S3UploadResource implements UploadResource {

    private final Logger LOGGER = LoggerFactory.getLogger(S3UploadResource.class);

    private final AmazonS3 s3Client;
    private final String bucketName;

    public S3UploadResource(AmazonS3 s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public UploadedPhoto upload(PhotoUploadRequest photoUploadRequest) {
        UUID uploadId = UUID.randomUUID();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadId.toString(), photoUploadRequest.getPhoto());
        PutObjectResult putObjectResult = s3Client.putObject(putObjectRequest);
        URL fileUploadUrl = s3Client.getUrl(bucketName, uploadId.toString());

        LOGGER.info("photo uploaded result={} url={} uploadId={}", putObjectResult, fileUploadUrl, uploadId);

        return new UploadedPhotoBuilder()
                .withUploadId(uploadId)
                .withTitle(photoUploadRequest.getTitle())
                .withDescription(photoUploadRequest.getDescription())
                .withTags(photoUploadRequest.getTags())
                .withCategories(photoUploadRequest.getCategories())
                .build();
    }
}
