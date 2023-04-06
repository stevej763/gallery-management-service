package com.steve.gallery.gallerymanagementservice.domain;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoCreator;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoBuilder.aPhotoDto;
import static com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder.aPhoto;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class PhotoCreatorTest {

    private final PhotoRepository photoRepository = mock(PhotoRepository.class);
    private final PhotoFactory photoFactory = mock(PhotoFactory.class);
    private final DtoFactory photoDtoFactory = mock(DtoFactory.class);
    private final UploadResource uploadResource = mock(UploadResource.class);

    private final PhotoCreator underTest = new PhotoCreator(photoRepository, photoFactory, photoDtoFactory, uploadResource);

    @Test
    public void processesPhotoUpload() {
        Photo photo = aPhoto().build();
        PhotoDto photoDto = aPhotoDto().build();
        File photoFile = mock(File.class);
        PhotoUploadRequest photoUploadRequest = new PhotoUploadRequestBuilder()
                .withTitle("title")
                .withDescription("description")
                .withTags(emptyList())
                .withCategories(emptyList())
                .withPhoto(photoFile)
                .build();

        UploadedPhoto uploadedPhoto = new UploadedPhotoBuilder().build();
        when(uploadResource.upload(photoUploadRequest)).thenReturn(uploadedPhoto);
        when(photoFactory.convert(uploadedPhoto)).thenReturn(photo);
        when(photoRepository.save(photo)).thenReturn(photo);
        when(photoDtoFactory.convert(photo)).thenReturn(photoDto);

        PhotoDto result = underTest.create(photoUploadRequest);

        assertThat(result, is(photoDto));
    }

}