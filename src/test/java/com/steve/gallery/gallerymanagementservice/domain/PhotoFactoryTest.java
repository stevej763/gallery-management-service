package com.steve.gallery.gallerymanagementservice.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PhotoFactoryTest {

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final List<String> TAGS = List.of("tag1", "tag2");
    private static final List<String> CATEGORIES = List.of("category1");

    @Test
    public void shouldConvertFromARequestToADomainPhotoObject() {
        PhotoFactory underTest = new PhotoFactory();

        Photo aPhoto = createAPhoto();
        PhotoUploadRequest photoUploadRequest = new PhotoUploadRequestBuilder()
                .withTitle(TITLE)
                .withDescription(DESCRIPTION)
                .withTags(TAGS)
                .withCategories(CATEGORIES)
                .build();

        Photo result = underTest.convert(photoUploadRequest);

        assertThat(reflectionEquals(aPhoto, result, "photoId", "createdAt", "modifiedAt"), is(true));
    }

    private Photo createAPhoto() {
        return PhotoBuilder.aPhoto()
                           .withPhotoId(UUID.randomUUID())
                           .withTitle(TITLE)
                           .withDescription(DESCRIPTION)
                           .withTags(TAGS)
                           .withCategories(CATEGORIES)
                           .withCreatedAt(LocalDateTime.now())
                           .withModifiedAt(LocalDateTime.now())
                           .build();
    }

}