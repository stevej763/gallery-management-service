package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PhotoUploadRequestFactoryTest {

    public static final UUID CATEGORY_ID = UUID.randomUUID();
    private final PhotoUploadRequestFactory underTest = new PhotoUploadRequestFactory();

    @Test
    public void shouldConvertToAPhotoUploadRequest() throws IOException {
        MockMultipartFile uploadedFile = new MockMultipartFile("test", "originalFileName", "jpeg", "content".getBytes());
        String title = "test";
        String description = "description";
        List<String> tags = List.of("tag");
        List<UUID> categories = List.of(CATEGORY_ID);
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

    @Test
    public void shouldThrowWhenOriginalFileNameIsNull() {
        String title = "test";
        MockMultipartFile uploadedFile = new MockMultipartFile(title, title.getBytes());
        String description = "description";
        List<String> tags = List.of("tag");
        List<UUID> categories = List.of(CATEGORY_ID);
        PhotoUploadMetadataDto photoUploadMetadataDto = new PhotoUploadMetadataDto(title, description, tags, categories);

        assertThrows(IOException.class, () -> underTest.createPhotoUploadRequest(uploadedFile, photoUploadMetadataDto));
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