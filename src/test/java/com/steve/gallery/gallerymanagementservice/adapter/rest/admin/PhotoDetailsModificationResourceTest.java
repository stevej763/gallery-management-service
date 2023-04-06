package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoFactory;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.resource.PhotoDetailsModificationResource;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.TagRequestDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.DescriptionEditRequestDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.TitleEditRequestDto;
import com.steve.gallery.gallerymanagementservice.domain.photo.edit.TagRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.edit.DescriptionEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.Photo;
import com.steve.gallery.gallerymanagementservice.domain.photo.edit.TitleEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDetailsEditor;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoBuilder.aPhotoDto;
import static com.steve.gallery.gallerymanagementservice.domain.photo.PhotoBuilder.aPhoto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoDetailsModificationResourceTest {

    public static final UUID PHOTO_ID = UUID.randomUUID();
    private final PhotoDetailsEditor photoDetailsEditor = mock(PhotoDetailsEditor.class);
    private final PhotoDtoFactory photoDtoFactory = mock(PhotoDtoFactory.class);
    private final PhotoDetailsModificationResource underTest = new PhotoDetailsModificationResource(photoDetailsEditor, photoDtoFactory);

    @Test
    public void canRequestAPhotoTitleToBeUpdated() {
        TitleEditRequestDto titleEditRequestDto = new TitleEditRequestDto("title");

        PhotoDto aPhotoDto = aPhotoDto().build();
        Photo aPhoto = aPhoto().build();
        TitleEditRequest titleEditRequest = new TitleEditRequest(PHOTO_ID, "title");
        when(photoDetailsEditor.editTitle(titleEditRequest)).thenReturn(aPhoto);
        when(photoDtoFactory.convert(aPhoto)).thenReturn(aPhotoDto);

        ResponseEntity<PhotoDto> result = underTest.editTitle(PHOTO_ID, titleEditRequestDto);

        assertThat(result, is(ResponseEntity.ok(aPhotoDto)));
    }

    @Test
    public void canRequestAPhotoDescriptionToBeUpdated() {
        DescriptionEditRequestDto descriptionEditRequestDto = new DescriptionEditRequestDto("description");

        PhotoDto aPhotoDto = aPhotoDto().build();
        Photo aPhoto = aPhoto().build();
        DescriptionEditRequest editRequest = new DescriptionEditRequest(PHOTO_ID, "description");
        when(photoDetailsEditor.editDescription(editRequest)).thenReturn(aPhoto);
        when(photoDtoFactory.convert(aPhoto)).thenReturn(aPhotoDto);

        ResponseEntity<PhotoDto> result = underTest.editDescription(PHOTO_ID, descriptionEditRequestDto);

        assertThat(result, is(ResponseEntity.ok(aPhotoDto)));
    }

    @Test
    public void canAddTag() {
        TagRequestDto tagRequestDto = new TagRequestDto("tagName");

        PhotoDto aPhotoDto = aPhotoDto().build();
        Photo aPhoto = aPhoto().build();
        TagRequest editRequest = new TagRequest(PHOTO_ID, "tagName");
        when(photoDetailsEditor.addTag(editRequest)).thenReturn(aPhoto);
        when(photoDtoFactory.convert(aPhoto)).thenReturn(aPhotoDto);

        ResponseEntity<PhotoDto> result = underTest.addTag(PHOTO_ID, tagRequestDto);

        assertThat(result, is(ResponseEntity.ok(aPhotoDto)));
    }

    @Test
    public void canRemoveTag() {
        TagRequestDto tagRequestDto = new TagRequestDto("tagName");

        PhotoDto aPhotoDto = aPhotoDto().build();
        Photo aPhoto = aPhoto().build();
        TagRequest editRequest = new TagRequest(PHOTO_ID, "tagName");
        when(photoDetailsEditor.removeTag(editRequest)).thenReturn(aPhoto);
        when(photoDtoFactory.convert(aPhoto)).thenReturn(aPhotoDto);

        ResponseEntity<PhotoDto> result = underTest.deleteTag(PHOTO_ID, tagRequestDto);

        assertThat(result, is(ResponseEntity.ok(aPhotoDto)));
    }
}