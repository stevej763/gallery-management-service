package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

public class PhotoUploadIntegrationTest extends BaseWebIntegrationTest {

    @Test
    public void canUploadANewPhoto() throws IOException {
        String photoTitle = "title";
        File photo = aFile();

        HttpEntity<MultiValueMap<String, Object>> request = createRequest(photo, photoTitle);

        ResponseEntity<PhotoDto> response = restTemplate.exchange(getGalleryAdminBasePath() + "/upload", POST, request, PhotoDto.class);

        photo.deleteOnExit();

        assertThat(response.getBody(), isA(PhotoDto.class));
        assertThat(response.getBody().getTitle(), is("title"));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    private HttpEntity<MultiValueMap<String, Object>> createRequest(File photo, String photoTitle) {
        MultiValueMap<String, Object> body = createRequestBody(photo, photoTitle);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MULTIPART_FORM_DATA);
        return new HttpEntity<>(body, headers);
    }

    private MultiValueMap<String, Object> createRequestBody(File photo, String photoTitle) {
        String categoryId1 = UUID.randomUUID().toString();
        String categoryId2 = UUID.randomUUID().toString();
        String categoryId3 = UUID.randomUUID().toString();
        String categories = String.format(categoryId1, categoryId2, categoryId3);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(photo));
        body.add("title", photoTitle);
        body.add("description", "description");
        body.add("tags", "tag1, tag2, tag3");
        body.add("categories", categories);
        return body;
    }
}
