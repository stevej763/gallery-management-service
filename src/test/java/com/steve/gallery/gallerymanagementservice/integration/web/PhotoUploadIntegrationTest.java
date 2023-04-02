package com.steve.gallery.gallerymanagementservice.integration.web;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoBuilder.aPhotoDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhotoUploadIntegrationTest {

    public static String BUCKET_NAME = "gallery-test";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
        s3Client.listObjects(BUCKET_NAME)
                .getObjectSummaries()
                .forEach(s3ObjectSummary -> s3Client.deleteObject(BUCKET_NAME, s3ObjectSummary.getKey()));
    }

    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void canUploadANewPhoto() throws IOException {
        MockMultipartFile uploadedFile = new MockMultipartFile("test", "originalFileName", "jpeg", "content".getBytes());

        File photo = new File(uploadedFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(photo);
        fileOutputStream.write(uploadedFile.getBytes());
        fileOutputStream.close();
        photo.deleteOnExit();

        String photoTitle = "title";
        String photoDescription = "description";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(photo));
        body.add("title", photoTitle);
        body.add("description", photoDescription);
        body.add("tags", "tag1, tag2, tag3");
        body.add("categories", "category1, category2");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<PhotoDto> response = restTemplate.exchange(getAdminBasePath() + "/upload", POST, request, PhotoDto.class);

        assertThat(response.getBody(), isA(PhotoDto.class));
        assertThat(response.getBody().getTitle(), is("title"));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    private String getAdminBasePath() {
        return "http://localhost:" + port + "/api/v1/admin";
    }
}
