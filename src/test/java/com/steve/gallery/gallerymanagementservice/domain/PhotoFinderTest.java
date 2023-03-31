package com.steve.gallery.gallerymanagementservice.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoFinderTest {

    private final PhotoRepository photoRepository = mock(PhotoRepository.class);
    private final PhotoFinder underTest = new PhotoFinder(photoRepository);

    @Test
    public void shouldReturnAllPhotos() {
        List<Photo> photosInRepository = List.of(new Photo(), new Photo(), new Photo());
        when(photoRepository.findAll()).thenReturn(photosInRepository);
        assertThat(underTest.findAll(), is(photosInRepository));
    }

    @Test
    public void shouldReturnEmptyListWhenThereAreNoPhotos() {
        when(photoRepository.findAll()).thenReturn(emptyList());
        assertThat(underTest.findAll(), is(emptyList()));
    }
}