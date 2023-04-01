package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MongoPhotoRepositoryTest {

    private final PhotoDao photoDao = mock(PhotoDao.class);
    private final MongoPhotoRepository underTest = new MongoPhotoRepository(photoDao);

    @Test
    public void shouldFindALlPhotos() {
        Photo photo = new Photo();
        when(photoDao.findAllPhotos()).thenReturn(List.of(photo));

        List<Photo> result = underTest.findAll();

        List<Photo> expected = List.of(photo);
        assertThat(result, is(expected));
    }

}