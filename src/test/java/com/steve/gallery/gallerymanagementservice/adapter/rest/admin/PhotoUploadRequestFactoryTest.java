package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;

public class PhotoUploadRequestFactoryTest {

    @Test
    public void shouldConvertToAPhotoUploadRequest() throws IOException {
        PhotoUploadRequestFactory underTest = new PhotoUploadRequestFactory();

        MockMultipartFile uploadedFile = new MockMultipartFile("test", "originalFileName", "jpeg", "content".getBytes());
        String title = "test";
        String description = "description";
        List<String> tags = List.of("tag");
        List<String> categories = List.of("category");
        PhotoUploadMetadataDto photoUploadMetadataDto = new PhotoUploadMetadataDto(title, description, tags, categories);
        PhotoUploadRequest result = underTest.createPhotoUploadRequest(uploadedFile, photoUploadMetadataDto);

        File photo = createFileFrom(uploadedFile);
        PhotoUploadRequest expected = new PhotoUploadRequestBuilder()
                .withTitle(title)
                .withDescription(description)
                .withTags(tags)
                .withCategories(categories)
                .withPhoto(photo)
                .build();
        assertThat(result, is(expected));
    }

    private File createFileFrom(MockMultipartFile uploadedFile) throws IOException {
        File photo = new File(uploadedFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(photo);
        fileOutputStream.write(uploadedFile.getBytes());
        fileOutputStream.close();
        photo.deleteOnExit();
        return photo;
    }

}