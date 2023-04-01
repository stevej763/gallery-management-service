package com.steve.gallery.gallerymanagementservice.domain;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import org.junit.jupiter.api.Test;

import static com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoBuilder.aPhotoDto;
import static com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder.aPhoto;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoCreationServiceTest {

    private final PhotoRepository photoRepository = mock(PhotoRepository.class);
    private final PhotoFactory photoFactory = mock(PhotoFactory.class);
    private final DtoFactory photoDtoFactory = mock(DtoFactory.class);

    private final PhotoCreationService underTest = new PhotoCreationService(photoRepository, photoFactory, photoDtoFactory);

    @Test
    public void processesPhotoUpload() {
        Photo photo = aPhoto().build();
        PhotoDto photoDto = aPhotoDto().build();
        PhotoUploadRequest photoUploadRequest = new PhotoUploadRequest("title", "description", emptyList(), emptyList());

        when(photoFactory.convert(photoUploadRequest)).thenReturn(photo);
        when(photoRepository.save(photo)).thenReturn(photo);
        when(photoDtoFactory.convert(photo)).thenReturn(photoDto);

        PhotoDto result = underTest.create(photoUploadRequest);

        assertThat(result, is(photoDto));
    }

}