package com.steve.gallery.gallerymanagementservice.domain.service;

import com.steve.gallery.gallerymanagementservice.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder.aPhoto;
import static java.util.Collections.emptyList;
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

        Photo original = createAPhoto(photoId, "original", "test", emptyList(), emptyList());
        Photo updated = createAPhoto(photoId, "new title", "test", emptyList(), emptyList());

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

        Photo original = createAPhoto(photoId, "original", "original", emptyList(), emptyList());
        Photo updated = createAPhoto(photoId, "original", "new description", emptyList(), emptyList());

        DescriptionEditRequest editRequest = new DescriptionEditRequest(photoId, "new description");
        when(photoFinder.findPhotoById(photoId)).thenReturn(original);
        when(photoRepository.updateDescription(editRequest)).thenReturn(updated);

        Photo result = underTest.editDescription(editRequest);

        verify(photoRepository).updateDescription(editRequest);
        assertThat(result, is(updated));
    }

    @Test
    public void canAddTagToPhoto() {
        UUID photoId = UUID.randomUUID();
        PhotoDetailsEditor underTest = new PhotoDetailsEditor(photoFinder, photoRepository);

        Photo original = createAPhoto(photoId, "original", "original", emptyList(), emptyList());
        Photo updated = createAPhoto(photoId, "original", "original", List.of("new tag"), emptyList());

        TagRequest editRequest = new TagRequest(photoId, "new tag");
        when(photoFinder.findPhotoById(photoId)).thenReturn(original);
        when(photoRepository.addTag(editRequest)).thenReturn(updated);

        Photo result = underTest.addTag(editRequest);

        verify(photoRepository).addTag(editRequest);
        assertThat(result, is(updated));
    }

    @Test
    public void doNotAttemptToAddTagIfItAlreadyExists() {
        UUID photoId = UUID.randomUUID();
        PhotoDetailsEditor underTest = new PhotoDetailsEditor(photoFinder, photoRepository);

        String tag = "tag";
        Photo original = createAPhoto(photoId, "original", "original", List.of(tag), emptyList());

        TagRequest editRequest = new TagRequest(photoId, tag);
        when(photoFinder.findPhotoById(photoId)).thenReturn(original);

        Photo result = underTest.addTag(editRequest);

        verifyNoInteractions(photoRepository);
        assertThat(result, is(original));
    }

    @Test
    public void canRemoveTagFromPhoto() {
        UUID photoId = UUID.randomUUID();
        PhotoDetailsEditor underTest = new PhotoDetailsEditor(photoFinder, photoRepository);

        String tagToRemove = "original tag";
        Photo original = createAPhoto(photoId, "original", "original", List.of(tagToRemove), emptyList());
        Photo updated = createAPhoto(photoId, "original", "original", emptyList(), emptyList());

        TagRequest editRequest = new TagRequest(photoId, tagToRemove);
        when(photoFinder.findPhotoById(photoId)).thenReturn(original);
        when(photoRepository.removeTag(editRequest)).thenReturn(updated);

        Photo result = underTest.removeTag(editRequest);

        verify(photoRepository).removeTag(editRequest);
        assertThat(result, is(updated));
    }

    @Test
    public void canRemoveCategoryFromAllPhotos() {
        UUID categoryId = UUID.randomUUID();
        PhotoDetailsEditor underTest = new PhotoDetailsEditor(photoFinder, photoRepository);

        List<UUID> categoryIdList = List.of(categoryId);
        Photo photo1 = createAPhoto(UUID.randomUUID(), "original", "original", List.of("tag"), categoryIdList);
        Photo photo1Updated = createAPhoto(UUID.randomUUID(), "original", "original", List.of("tag"), emptyList());
        Photo photo2 = createAPhoto(UUID.randomUUID(), "original", "original", List.of("tag"), categoryIdList);
        Photo photo2Updated = createAPhoto(UUID.randomUUID(), "original", "original", List.of("tag"), emptyList());

        when(photoFinder.findAllByCategory(categoryId)).thenReturn(List.of(photo1, photo2));
        when(photoRepository.removeCategory(new CategoryRequest(photo1.getPhotoId(), categoryId))).thenReturn(photo1Updated);
        when(photoRepository.removeCategory(new CategoryRequest(photo2.getPhotoId(), categoryId))).thenReturn(photo2Updated);

        List<Photo> result = underTest.removeCategoryFromAllPhotos(new CategoryDeletionRequest(categoryId));

        assertThat(result, is(List.of(photo1Updated, photo2Updated)));
    }

    @Test
    public void doNotAttemptToRemoveTagIfItDoesNotExist() {
        UUID photoId = UUID.randomUUID();
        PhotoDetailsEditor underTest = new PhotoDetailsEditor(photoFinder, photoRepository);

        String tagToRemove = "original tag";
        Photo original = createAPhoto(photoId, "original", "original", emptyList(), emptyList());

        TagRequest editRequest = new TagRequest(photoId, tagToRemove);
        when(photoFinder.findPhotoById(photoId)).thenReturn(original);

        Photo result = underTest.removeTag(editRequest);

        verifyNoInteractions(photoRepository);
        assertThat(result, is(original));
    }

    private Photo createAPhoto(UUID photoId, String original, String description, List<String> tags, List<UUID> categoryIds) {
        return aPhoto()
                .withPhotoId(photoId)
                .withTitle(original)
                .withDescription(description)
                .withTags(tags).withCategories(categoryIds)
                .build();
    }
}