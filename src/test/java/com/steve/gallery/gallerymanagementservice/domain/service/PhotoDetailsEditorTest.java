package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.DescriptionEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;
import com.steve.gallery.gallerymanagementservice.domain.TitleEditRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder.aPhoto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class PhotoDetailsEditorTest {

    private final PhotoFinder photoFinder = mock(PhotoFinder.class);
    private final PhotoRepository photoRepository = mock(PhotoRepository.class);

    @Test
    public void canEditPhotoTitle() {
        UUID photoId = UUID.randomUUID();
        PhotoDetailsEditor underTest = new PhotoDetailsEditor(photoFinder, photoRepository);

        Photo original = createAPhoto(photoId, "original", "test");
        Photo updated = createAPhoto(photoId, "new title", "test");

        TitleEditRequest editRequest = new TitleEditRequest(photoId, "new title");
        when(photoFinder.findPhotoById(photoId)).thenReturn(original);
        when(photoRepository.updateTitle(editRequest)).thenReturn(updated);

        Photo result = underTest.editTitle(editRequest);

        verify(photoRepository).updateTitle(editRequest);
        assertThat(result, is(updated));
    }

    @Test
    public void canEditPhotoDescription() {
        UUID photoId = UUID.randomUUID();
        PhotoDetailsEditor underTest = new PhotoDetailsEditor(photoFinder, photoRepository);

        Photo original = createAPhoto(photoId, "original", "original");
        Photo updated = createAPhoto(photoId, "original", "new description");

        DescriptionEditRequest editRequest = new DescriptionEditRequest(photoId, "new description");
        when(photoFinder.findPhotoById(photoId)).thenReturn(original);
        when(photoRepository.updateDescription(editRequest)).thenReturn(updated);

        Photo result = underTest.editDescription(editRequest);

        verify(photoRepository).updateDescription(editRequest);
        assertThat(result, is(updated));
    }

    private Photo createAPhoto(UUID photoId, String original, String description) {
        return aPhoto()
                .withPhotoId(photoId)
                .withTitle(original)
                .withDescription(description)
                .build();
    }
}