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

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.UUID;

public class S3UploadResource implements UploadResource {

    private final Logger LOGGER = LoggerFactory.getLogger(S3UploadResource.class);

    private final AmazonS3 s3Client;
    private final String bucketName;
    private final S3UploadRequestFactory s3UploadRequestFactory;

    public S3UploadResource(AmazonS3 s3Client, String bucketName, S3UploadRequestFactory s3UploadRequestFactory) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.s3UploadRequestFactory = s3UploadRequestFactory;
    }

    @Override
    public UploadedPhoto upload(PhotoUploadRequest photoUploadRequest) {
        try {
            S3UploadRequest s3UploadRequest = s3UploadRequestFactory.create(bucketName, photoUploadRequest);
            String uploadIdAsString = s3UploadRequest.getUploadIdAsString();
            PutObjectRequest putObjectRequest = s3UploadRequest.getPutObjectRequest();

            PutObjectResult putObjectResult = s3Client.putObject(putObjectRequest);
            URL fileUploadUrl = s3Client.getUrl(bucketName, uploadIdAsString);

            LOGGER.info("photo uploaded result={} url={} uploadId={}", putObjectResult, fileUploadUrl, uploadIdAsString);
            return createUploadedPhoto(photoUploadRequest, s3UploadRequest);
        } catch (Exception e) {
            LOGGER.error("Failed to upload", e);
            throw new S3FileUploadException("File failed preparation for upload, or upload failed", e);
        }
    }

    private UploadedPhoto createUploadedPhoto(PhotoUploadRequest photoUploadRequest, S3UploadRequest s3UploadRequest) {
        return new UploadedPhotoBuilder()
                .withUploadId(s3UploadRequest.getUploadId())
                .withTitle(photoUploadRequest.getTitle())
                .withDescription(photoUploadRequest.getDescription())
                .withTags(photoUploadRequest.getTags())
                .withCategories(photoUploadRequest.getCategories())
                .build();
    }
}
