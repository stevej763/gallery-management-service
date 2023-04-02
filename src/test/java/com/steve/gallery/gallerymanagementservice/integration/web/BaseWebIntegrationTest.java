package com.steve.gallery.gallerymanagementservice.integration.web;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseWebIntegrationTest {

    public static String BUCKET_NAME = "gallery-test";

    @Value(value = "${local.server.port}")
    protected int port;

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected AmazonS3 s3Client;

    @Autowired
    protected TestRestTemplate restTemplate;

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

    protected void createFileInBucket(UUID uploadId) throws IOException {
        File file = aFile();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("jpeg");
        metadata.setContentLength(file.length());

        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, uploadId.toString(), new FileInputStream(file), metadata);
        s3Client.putObject(putObjectRequest);
        file.deleteOnExit();
    }

    protected File aFile() throws IOException {
        MockMultipartFile uploadedFile = new MockMultipartFile("test", "originalFileName", "jpeg", "content".getBytes());

        File photo = new File(uploadedFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(photo);
        fileOutputStream.write(uploadedFile.getBytes());
        fileOutputStream.close();
        return photo;
    }

    protected void savePhotoToDatabase(Photo photo) {
        mongoTemplate.save(photo, "photos");
    }

    protected String getGalleryBasePath() {
        return "http://localhost:" + port + "/api/v1/gallery";
    }

    protected String getAdminBasePath() {
        return "http://localhost:" + port + "/api/v1/admin";
    }
}
