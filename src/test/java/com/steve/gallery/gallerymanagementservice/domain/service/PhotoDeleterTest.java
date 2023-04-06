package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.*;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder.aPhoto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class PhotoDeleterTest {

    private final PhotoRepository photoRepository = mock(PhotoRepository.class);
    private final DeletionResource deletionResource = mock(DeletionResource.class);
    private final PhotoDeleter underTest = new PhotoDeleter(photoRepository, deletionResource);

    @Test
    public void shouldDeletePhoto() {
        UUID photoId = UUID.randomUUID();
        UUID uploadId = UUID.randomUUID();
        Photo photo = aPhoto()
                .withPhotoId(photoId)
                .withUploadId(uploadId)
                .build();

        when(photoRepository.findById(photoId)).thenReturn(photo).thenReturn(photo);
        when(photoRepository.delete(photoId)).thenReturn(true);
        when(deletionResource.deleteFile(uploadId)).thenReturn(true);
        PhotoDeletionResponse result = underTest.deletePhoto(photoId);

        PhotoDeletionResponse expected = new PhotoDeletionResponse(photoId, true, true);
        verify(photoRepository).delete(photoId);
        verify(deletionResource).deleteFile(uploadId);
        assertThat(result, is(expected));
    }

    @Test
    public void shouldReturnErrorWhenPhotoDoesNotExist() {
        UUID photoId = UUID.randomUUID();

        when(photoRepository.findById(photoId)).thenReturn(null);
        PhotoDeletionResponse result = underTest.deletePhoto(photoId);

        PhotoDeletionResponse expected = new PhotoDeletionResponse(photoId, false, false);
        verify(photoRepository, never()).delete(photoId);
        verifyNoInteractions(deletionResource);
        assertThat(result, is(expected));
    }

    @Test
    public void shouldReturnResponseWhenErrorDeletingRecordFromDatabase() {
        UUID photoId = UUID.randomUUID();
        UUID uploadId = UUID.randomUUID();
        Photo photo = aPhoto()
                .withPhotoId(photoId)
                .withUploadId(uploadId)
                .build();

        when(photoRepository.findById(photoId)).thenReturn(photo).thenReturn(photo);
        when(photoRepository.delete(photoId)).thenReturn(false);
        when(deletionResource.deleteFile(uploadId)).thenReturn(true);

        PhotoDeletionResponse result = underTest.deletePhoto(photoId);

        PhotoDeletionResponse expected = new PhotoDeletionResponse(photoId, false, true);
        verify(photoRepository).delete(photoId);
        verify(deletionResource).deleteFile(uploadId);
        assertThat(result, is(expected));
    }

    @Test
    public void shouldReturnResponseWhenErrorDeletingFile() {
        UUID photoId = UUID.randomUUID();
        UUID uploadId = UUID.randomUUID();
        Photo photo = aPhoto()
                .withPhotoId(photoId)
                .withUploadId(uploadId)
                .build();

        when(photoRepository.findById(photoId)).thenReturn(photo).thenReturn(photo);
        when(photoRepository.delete(photoId)).thenReturn(true);
        when(deletionResource.deleteFile(uploadId)).thenReturn(false);
        PhotoDeletionResponse result = underTest.deletePhoto(photoId);

        PhotoDeletionResponse expected = new PhotoDeletionResponse(photoId, true, false);
        verify(photoRepository).delete(photoId);
        verify(deletionResource).deleteFile(uploadId);
        assertThat(result, is(expected));
    }
}