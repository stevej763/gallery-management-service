package com.steve.gallery.gallerymanagementservice.adapter.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.UploadedPhoto;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequestBuilder.aPhotoUploadRequest;
import static com.steve.gallery.gallerymanagementservice.domain.UploadedPhotoBuilder.*;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class S3UploadResourceTest {

    @Test
    public void shouldUploadFile() {
        AmazonS3 s3Client = mock(AmazonS3.class);
        String bucketName = "bucketName";
        File file = mock(File.class);
        S3UploadResource underTest = new S3UploadResource(s3Client, bucketName);
        String photoToUpload = "photoToUpload";
        String description = "description";
        List<String> categories = List.of(UUID.randomUUID().toString());
        List<String> tags = List.of(UUID.randomUUID().toString());
        PhotoUploadRequest photoUploadRequest = aPhotoUploadRequest()
                .withPhoto(file)
                .withTitle(photoToUpload)
                .withDescription(description)
                .withCategories(categories)
                .withTags(tags)
                .build();
        UploadedPhoto uploadedPhoto = underTest.upload(photoUploadRequest);

        UploadedPhoto expected = anUploadedPhoto()
                .withTitle(photoToUpload)
                .withDescription(description)
                .withCategories(categories)
                .withTags(tags)
                .withUploadId(UUID.randomUUID())
                .build();
        verify(s3Client).putObject(ArgumentMatchers.any(PutObjectRequest.class));
        assertThat(reflectionEquals(uploadedPhoto, expected, "uploadId"), is(true));
        assertThat(uploadedPhoto.getUploadId(), notNullValue());
    }

}