package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MongoPhotoRepositoryTest {

    private final MongoTemplate mongoTemplate = mock(MongoTemplate.class);
    private final MongoPhotoRepository underTest = new MongoPhotoRepository(mongoTemplate);

    @Test
    public void shouldFindALlPhotos() {
        Photo photo = new Photo();
        when(mongoTemplate.findAll(Photo.class, "photos")).thenReturn(List.of(photo));

        List<Photo> result = underTest.findAll();

        List<Photo> expected = List.of(photo);
        assertThat(result, is(expected));
    }

}