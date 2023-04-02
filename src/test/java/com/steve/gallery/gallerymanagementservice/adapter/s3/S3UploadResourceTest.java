package com.steve.gallery.gallerymanagementservice.adapter.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.UploadedPhoto;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequestBuilder.aPhotoUploadRequest;
import static com.steve.gallery.gallerymanagementservice.domain.UploadedPhotoBuilder.anUploadedPhoto;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class S3UploadResourceTest {

    private final String bucketName = "bucketName";

    private final AmazonS3 s3Client = mock(AmazonS3.class);
    private final S3UploadRequestFactory s3UploadRequestFactory = mock(S3UploadRequestFactory.class);

    private final S3UploadResource underTest = new S3UploadResource(s3Client, bucketName, s3UploadRequestFactory);

    @Test
    public void shouldUploadFile() throws FileNotFoundException {
        File file = mock(File.class);
        UUID uploadId = UUID.randomUUID();
        String photoToUpload = "photoToUpload";
        String description = "description";
        List<String> categories = List.of(UUID.randomUUID().toString());
        List<String> tags = List.of(UUID.randomUUID().toString());

        PhotoUploadRequest photoUploadRequest = createPhotoUploadRequest(file, photoToUpload, description, categories, tags);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadId.toString(), mock(FileInputStream.class), new ObjectMetadata());
        when(s3UploadRequestFactory.create(bucketName, photoUploadRequest)).thenReturn(new S3UploadRequest(putObjectRequest, uploadId));

        UploadedPhoto uploadedPhoto = underTest.upload(photoUploadRequest);

        UploadedPhoto expected = createUploadedPhoto(photoToUpload, description, categories, tags, uploadId);

        verify(s3Client).putObject(putObjectRequest);
        assertThat(uploadedPhoto, is(expected));
    }

    @Test
    public void catchesExceptionWhenS3UploadRequestFactoryFails() throws FileNotFoundException {
        File file = mock(File.class);
        String photoToUpload = "photoToUpload";
        String description = "description";
        List<String> categories = List.of(UUID.randomUUID().toString());
        List<String> tags = List.of(UUID.randomUUID().toString());

        PhotoUploadRequest photoUploadRequest = createPhotoUploadRequest(file, photoToUpload, description, categories, tags);
        when(s3UploadRequestFactory.create(bucketName, photoUploadRequest)).thenThrow(new FileNotFoundException("test"));

        assertThrows(S3FileUploadException.class, () -> underTest.upload(photoUploadRequest));
        verifyNoInteractions(s3Client);
    }

    private UploadedPhoto createUploadedPhoto(
            String photoToUpload,
            String description,
            List<String> categories,
            List<String> tags,
            UUID uploadId) {
        return anUploadedPhoto()
                .withTitle(photoToUpload)
                .withDescription(description)
                .withCategories(categories)
                .withTags(tags)
                .withUploadId(uploadId)
                .build();
    }

    private PhotoUploadRequest createPhotoUploadRequest(
            File file,
            String photoToUpload,
            String description,
            List<String> categories,
            List<String> tags) {
        return aPhotoUploadRequest()
                .withPhoto(file)
                .withTitle(photoToUpload)
                .withDescription(description)
                .withCategories(categories)
                .withTags(tags)
                .build();
    }

}