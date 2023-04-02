package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;
import com.steve.gallery.gallerymanagementservice.domain.TitleEditRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime modifiedAt = LocalDateTime.now().minusDays(1);

        Photo original = aPhoto()
                .withPhotoId(photoId)
                .withTitle("original")
                .withCreatedAt(createdAt)
                .withModifiedAt(createdAt)
                .build();
        Photo updated = aPhoto()
                .withPhotoId(photoId)
                .withTitle("new title")
                .withCreatedAt(createdAt)
                .withModifiedAt(modifiedAt)
                .build();

        when(photoFinder.findPhotoById(photoId)).thenReturn(original);
        when(photoRepository.updateTitle(any(Photo.class))).thenReturn(updated);

        Photo result = underTest.editTitle(new TitleEditRequest(photoId, "new title"));

        verify(photoRepository).updateTitle(any(Photo.class));
        verify(photoRepository, never()).updateTitle(original);
        assertThat(result, is(updated));
    }
}