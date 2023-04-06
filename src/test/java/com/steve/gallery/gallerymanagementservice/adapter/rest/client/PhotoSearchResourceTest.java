package com.steve.gallery.gallerymanagementservice.adapter.rest.client;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoBuilder;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoFactory;
import com.steve.gallery.gallerymanagementservice.domain.photo.Photo;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoBuilder;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoFinder;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoSearchResourceTest {

    private static final UUID PHOTO_ID_1 = UUID.randomUUID();
    private static final UUID PHOTO_ID_2 = UUID.randomUUID();
    private static final UUID PHOTO_ID_3 = UUID.randomUUID();
    private static final String PHOTO_TITLE = "title";
    private final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();


    private final PhotoFinder photoFinder = mock(PhotoFinder.class);
    private final PhotoDtoFactory photoDtoFactory = mock(PhotoDtoFactory.class);
    private final PhotoSearchResource underTest = new PhotoSearchResource(photoFinder, photoDtoFactory);

    @Test
    public void canReturnAllPhotos() {
        Photo photo1 = aPhoto(PHOTO_ID_1);
        Photo photo2 = aPhoto(PHOTO_ID_2);
        Photo photo3 = aPhoto(PHOTO_ID_3);
        PhotoDto photoDto1 = aPhotoDto(PHOTO_ID_1);
        PhotoDto photoDto2 = aPhotoDto(PHOTO_ID_2);
        PhotoDto photoDto3 = aPhotoDto(PHOTO_ID_3);
        List<Photo> photos = List.of(photo1, photo2, photo3);
        List<PhotoDto> photoDtoList = List.of(photoDto1, photoDto2, photoDto3);
        when(photoFinder.findAll()).thenReturn(photos);

        when(photoDtoFactory.convert(photo1)).thenReturn(photoDto1);
        when(photoDtoFactory.convert(photo2)).thenReturn(photoDto2);
        when(photoDtoFactory.convert(photo3)).thenReturn(photoDto3);

        ResponseEntity<List<PhotoDto>> result = underTest.photos();


        ResponseEntity<List<PhotoDto>> expected = ResponseEntity.ok(photoDtoList);
        assertThat(result, is(expected));
    }

    @Test
    public void returnsPhotoByPhotoId() {
        Photo photo = aPhoto(PHOTO_ID_1);
        PhotoDto photoDto = aPhotoDto(PHOTO_ID_1);

        when(photoFinder.findPhotoById(PHOTO_ID_1)).thenReturn(photo);
        when(photoDtoFactory.convert(photo)).thenReturn(photoDto);

        ResponseEntity<PhotoDto> result = underTest.findPhotoById(PHOTO_ID_1);

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

    @Test
    public void returnsMatchesByTitle() {
        String photoTitle = "my photo";
        Photo photo = aPhoto(PHOTO_ID_1);
        PhotoDto photoDto = aPhotoDto(PHOTO_ID_1);

        when(photoFinder.findPhotoByTitle(photoTitle)).thenReturn(List.of(photo));
        when(photoDtoFactory.convert(photo)).thenReturn(photoDto);

        ResponseEntity<List<PhotoDto>> result = underTest.findPhotoByTitle(photoTitle);

        List<PhotoDto> photoDtoList = List.of(photoDto);
        ResponseEntity<List<PhotoDto>> expected = ResponseEntity.ok(photoDtoList);
        assertThat(result, is(expected));

    }

    private PhotoDto aPhotoDto(UUID id) {
        return new PhotoDtoBuilder()
                .withPhotoId(id)
                .withTitle(PHOTO_TITLE)
                .withCreatedAt(LOCAL_DATE_TIME)
                .withModifiedAt(LOCAL_DATE_TIME)
                .build();
    }

    private Photo aPhoto(UUID id) {
        return new PhotoBuilder()
                .withPhotoId(id)
                .withTitle(PHOTO_TITLE)
                .withCreatedAt(LOCAL_DATE_TIME)
                .withModifiedAt(LOCAL_DATE_TIME)
                .build();
    }
}
