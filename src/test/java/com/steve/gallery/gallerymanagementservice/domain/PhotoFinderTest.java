package com.steve.gallery.gallerymanagementservice.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoFinderTest {

    private final PhotoRepository photoRepository = mock(PhotoRepository.class);
    private final PhotoFinder underTest = new PhotoFinder(photoRepository);

    @Test
    public void shouldReturnAllPhotos() {
        Photo photo1 = aPhoto();
        Photo photo2 = aPhoto();
        Photo photo3 = aPhoto();
        List<Photo> photosInRepository = List.of(photo1, photo2, photo3);
        when(photoRepository.findAll()).thenReturn(photosInRepository);
        assertThat(underTest.findAll(), is(photosInRepository));
    }

    @Test
    public void shouldReturnPhotoById() {
        UUID photoId = UUID.randomUUID();
        Photo photo = new PhotoBuilder()
                .withPhotoId(photoId)
                .build();
        when(photoRepository.findById(photoId)).thenReturn(photo);

        Photo result = underTest.findPhotoById(photoId);

        assertThat(result, is(photo));
    }

    @Test
    public void shouldReturnPhotosByTitleSearch() {
        UUID photoId = UUID.randomUUID();
        String photoTitle = "photoTitle";
        Photo photo = new PhotoBuilder()
                .withTitle(photoTitle)
                .withPhotoId(photoId)
                .build();
        when(photoRepository.findByTitle(photoTitle)).thenReturn(List.of(photo));

        List<Photo> result = underTest.findPhotoByTitle(photoTitle);

        assertThat(result, is(List.of(photo)));
    }

    @Test
    public void shouldReturnEmptyListWhenThereAreNoPhotos() {
        when(photoRepository.findAll()).thenReturn(emptyList());
        assertThat(underTest.findAll(), is(emptyList()));
    }

    private Photo aPhoto() {
        return new PhotoBuilder().build();
    }
}