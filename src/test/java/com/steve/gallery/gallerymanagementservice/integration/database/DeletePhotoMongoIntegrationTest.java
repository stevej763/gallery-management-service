package com.steve.gallery.gallerymanagementservice.integration.database;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.*;
import com.steve.gallery.gallerymanagementservice.adapter.s3.S3DeletionResource;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import com.steve.gallery.gallerymanagementservice.domain.PhotoDeletionResponse;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFactory;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDeleter;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DeletePhotoMongoIntegrationTest extends BaseMongoIntegrationTest {

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final UUID UPLOAD_ID = UUID.randomUUID();
    private static final Photo PHOTO = new PhotoBuilder()
            .withPhotoId(PHOTO_ID)
            .withUploadId(UPLOAD_ID)
            .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();
    private static final PhotoMetadata PHOTO_METADATA = new PhotoMetadataBuilder()
            .withPhotoId(PHOTO_ID)
            .withUploadId(UPLOAD_ID)
            .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();

    @Test
    public void canDeleteUploadedPhoto() throws IOException {
        prepareTestPhoto();
        MongoPhotoRepository mongoPhotoRepository = new MongoPhotoRepository(new PhotoDao(mongoTemplate), new PhotoFactory(), new PhotoMetadataFactory());
        S3DeletionResource deletionResource = new S3DeletionResource(s3Client, BUCKET_NAME);
        PhotoDeleter photoDeleter = new PhotoDeleter(mongoPhotoRepository, deletionResource);

        PhotoDeletionResponse expected = new PhotoDeletionResponse(PHOTO_ID, true, true);
        assertThat(photoDeleter.deletePhoto(PHOTO_ID), is(expected));
    }

    private void prepareTestPhoto() throws IOException {
        savePhotoToDatabase(PHOTO_METADATA);
        createFileInBucket();
    }


    private void createFileInBucket() throws IOException {
        File file = aFile();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("jpeg");
        metadata.setContentLength(file.length());

        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, UPLOAD_ID.toString(), new FileInputStream(file), metadata);
        s3Client.putObject(putObjectRequest);
        file.deleteOnExit();
    }

    private File aFile() throws IOException {
        MockMultipartFile uploadedFile = new MockMultipartFile("test", "originalFileName", "jpeg", "content".getBytes());

        File photo = new File(uploadedFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(photo);
        fileOutputStream.write(uploadedFile.getBytes());
        fileOutputStream.close();
        return photo;
    }
}
