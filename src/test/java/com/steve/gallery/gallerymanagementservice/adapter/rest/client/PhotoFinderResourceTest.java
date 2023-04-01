package com.steve.gallery.gallerymanagementservice.adapter.rest.client;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFinder;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoFinderResourceTest {

    public static final UUID PHOTO_ID_1 = UUID.randomUUID();
    public static final UUID PHOTO_ID_2 = UUID.randomUUID();
    public static final UUID PHOTO_ID_3 = UUID.randomUUID();
    private final PhotoFinder photoFinder = mock(PhotoFinder.class);
    private final PhotoFinderResource underTest = new PhotoFinderResource(photoFinder);

    @Test
    public void canReturnAllPhotos() {
        List<Photo> photos = List.of(
                aPhoto(PHOTO_ID_1),
                aPhoto(PHOTO_ID_2),
                aPhoto(PHOTO_ID_3));

        when(photoFinder.findAll()).thenReturn(photos);

        ResponseEntity<List<PhotoDto>> result = underTest.photos();

        List<PhotoDto> photoDtoList = List.of(
                aPhotoDto(PHOTO_ID_1),
                aPhotoDto(PHOTO_ID_2),
                aPhotoDto(PHOTO_ID_3));

        ResponseEntity<List<PhotoDto>> expected = ResponseEntity.ok(photoDtoList);
        assertThat(result, is(expected));
    }

    @Test
    public void returnsPhotoByPhotoId() {
        Photo photo = aPhoto(PHOTO_ID_1);

        when(photoFinder.findPhotoById(PHOTO_ID_1)).thenReturn(photo);

        ResponseEntity<PhotoDto> result = underTest.findPhotoById(PHOTO_ID_1);

        PhotoDto photoDto = aPhotoDto(PHOTO_ID_1);
        ResponseEntity<PhotoDto> expected = ResponseEntity.ok(photoDto);
        assertThat(result, is(expected));
    }

    @Test
    public void returnsNotFoundErrorWhenIdDoesNotExist() {
        when(photoFinder.findPhotoById(PHOTO_ID_1)).thenReturn(null);

        ResponseEntity<PhotoDto> result = underTest.findPhotoById(PHOTO_ID_1);

        ResponseEntity<PhotoDto> expected = ResponseEntity.notFound().build();
        assertThat(result, is(expected));
    }

    private PhotoDto aPhotoDto(UUID id) {
        return new PhotoDtoBuilder().withPhotoId(id).build();
    }

    private Photo aPhoto(UUID id) {
        return new PhotoBuilder().withPhotoId(id).build();
    }
}
