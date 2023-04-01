package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MongoPhotoRepositoryTest {

    private final PhotoDao photoDao = mock(PhotoDao.class);
    private final MongoPhotoRepository underTest = new MongoPhotoRepository(photoDao);

    @Test
    public void shouldFindAllPhotos() {
        Photo photo = new PhotoBuilder().build();
        when(photoDao.findAllPhotos()).thenReturn(List.of(photo));

        List<Photo> result = underTest.findAll();

        List<Photo> expected = List.of(photo);
        assertThat(result, is(expected));
    }

    @Test
    public void shouldFindPhotosByTitle() {
        String photoTitle = "title";
        Photo photo = new PhotoBuilder().withTitle(photoTitle).build();
        when(photoDao.findPhotoByTitle(photoTitle)).thenReturn(List.of(photo));

        List<Photo> result = underTest.findByTitle(photoTitle);

        List<Photo> expected = List.of(photo);
        assertThat(result, is(expected));
    }

    @Test
    public void shouldFindPhotoById() {
        UUID photoId = UUID.randomUUID();
        Photo photo = new PhotoBuilder().withPhotoId(photoId).build();
        when(photoDao.findPhotoById(photoId)).thenReturn(photo);

        Photo result = underTest.findById(photoId);

        assertThat(result, is(photo));
    }

    @Test
    public void shouldSavePhoto() {
        UUID photoId = UUID.randomUUID();
        Photo photo = new PhotoBuilder().withPhotoId(photoId).build();
        when(photoDao.save(photo)).thenReturn(photo);

        Photo result = underTest.save(photo);

        assertThat(result, is(photo));
    }
}
