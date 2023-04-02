package com.steve.gallery.gallerymanagementservice.integration.web;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.PhotoDeletionResponseDto;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import static org.springframework.http.HttpMethod.DELETE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhotoDeletionIntegrationTest {

    public static String BUCKET_NAME = "gallery-test";

    private static final UUID PHOTO_ID = UUID.randomUUID();
    private static final UUID UPLOAD_ID = UUID.randomUUID();
    private static final Photo PHOTO = new PhotoBuilder()
            .withPhotoId(PHOTO_ID)
            .withUploadId(UPLOAD_ID)
            .withModifiedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .withCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
            .build();

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
        s3Client.listObjects(BUCKET_NAME).getObjectSummaries()
                .forEach(s3ObjectSummary -> s3Client.deleteObject(BUCKET_NAME, s3ObjectSummary.getKey()));
    }

    @Test
    public void canDeleteAPhoto() throws IOException {
        prepareTestPhoto();

        String deletionUrl = getAdminBasePath() + "/delete/" + PHOTO_ID;
        ResponseEntity<PhotoDeletionResponseDto> response = restTemplate.exchange(deletionUrl, DELETE, null, PhotoDeletionResponseDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new PhotoDeletionResponseDto(true)));
    }

    private String getAdminBasePath() {
        return "http://localhost:" + port + "/api/v1/admin";
    }

    private void prepareTestPhoto() throws IOException {
        savePhotoToDatabase();
        createFileInBucket();
    }

    private void savePhotoToDatabase() {
        mongoTemplate.save(PHOTO, "photos");
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
