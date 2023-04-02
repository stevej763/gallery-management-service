package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoFactory;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.TitleEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDetailsEditor;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoBuilder.aPhotoDto;
import static com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder.aPhoto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoDetailsModificationResourceTest {

    private final PhotoDetailsEditor photoDetailsEditor = mock(PhotoDetailsEditor.class);
    private final PhotoDtoFactory photoDtoFactory = mock(PhotoDtoFactory.class);

    @Test
    public void canRequestAPhotoTitleToBeUpdated() {
        PhotoDetailsModificationResource underTest = new PhotoDetailsModificationResource(photoDetailsEditor, photoDtoFactory);

        UUID photoId = UUID.randomUUID();
        TitleEditRequestDto titleEditRequestDto = new TitleEditRequestDto(photoId, "title");

        PhotoDto aPhotoDto = aPhotoDto().build();
        Photo aPhoto = aPhoto().build();
        TitleEditRequest titleEditRequest = new TitleEditRequest(photoId, "title");
        when(photoDetailsEditor.editTitle(titleEditRequest)).thenReturn(aPhoto);
        when(photoDtoFactory.convert(aPhoto)).thenReturn(aPhotoDto);

        ResponseEntity<PhotoDto> result = underTest.editTitle(titleEditRequestDto);

        assertThat(result, is(ResponseEntity.ok(aPhotoDto)));
    }

}