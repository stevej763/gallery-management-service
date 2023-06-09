package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import com.steve.gallery.gallerymanagementservice.domain.category.CategoryRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.*;
import com.steve.gallery.gallerymanagementservice.domain.photo.edit.DescriptionEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.edit.TagRequest;
import com.steve.gallery.gallerymanagementservice.domain.photo.edit.TitleEditRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata.CATEGORIES;
import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadata.TAGS;
import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadataBuilder.aPhotoMetadata;
import static com.steve.gallery.gallerymanagementservice.domain.photo.PhotoBuilder.aPhoto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MongoPhotoRepositoryTest {

    private final PhotoDao photoDao = mock(PhotoDao.class);
    private final PhotoFactory photoFactory = mock(PhotoFactory.class);
    private final PhotoMetadataFactory photoMetadataFactory = mock(PhotoMetadataFactory.class);
    private final MongoPhotoRepository underTest = new MongoPhotoRepository(photoDao, photoFactory, photoMetadataFactory);

    @Test
    public void shouldFindAllPhotos() {
        PhotoMetadata photoMetadata1 = aPhotoMetadata().build();
        PhotoMetadata photoMetadata2 = aPhotoMetadata().build();
        PhotoMetadata photoMetadata3 = aPhotoMetadata().build();
        Photo photo1 = aPhoto().build();
        Photo photo2 = aPhoto().build();
        Photo photo3 = aPhoto().build();
        when(photoDao.findAllPhotos()).thenReturn(List.of(photoMetadata1, photoMetadata2, photoMetadata3));
        when(photoFactory.convert(photoMetadata1)).thenReturn(photo1);
        when(photoFactory.convert(photoMetadata2)).thenReturn(photo2);
        when(photoFactory.convert(photoMetadata3)).thenReturn(photo3);

        List<Photo> result = underTest.findAll();

        List<Photo> expected = List.of(photo1, photo2, photo3);
        assertThat(result, is(expected));
    }

    @Test
    public void shouldFindPhotoById() {
        UUID photoId = UUID.randomUUID();

        PhotoMetadata photoMetadata = createPhotoMetadata(photoId);
        Photo photo = aPhoto().build();
        when(photoDao.findPhotoById(photoId)).thenReturn(photoMetadata);
        when(photoFactory.convert(photoMetadata)).thenReturn(photo);

        Photo result = underTest.findById(photoId);

        assertThat(result, is(photo));
    }

    @Test
    public void shouldFindPhotosByTitle() {
        String photoTitle = "title";
        PhotoMetadata photoMetadata = aPhotoMetadata().build();
        Photo photo = aPhoto().build();
        when(photoDao.findPhotoByTitle(photoTitle)).thenReturn(List.of(photoMetadata));
        when(photoFactory.convert(photoMetadata)).thenReturn(photo);

        List<Photo> result = underTest.findByTitle(photoTitle);

        List<Photo> expected = List.of(photo);
        assertThat(result, is(expected));
    }

    @Test
    public void shouldSavePhoto() {
        UUID photoId = UUID.randomUUID();
        PhotoMetadata photoMetadata = createPhotoMetadata(photoId);
        Photo photo = aPhoto().build();
        when(photoDao.save(photoMetadata)).thenReturn(photoMetadata);
        when(photoMetadataFactory.convert(photo)).thenReturn(photoMetadata);
        when(photoFactory.convert(photoMetadata)).thenReturn(photo);

        Photo result = underTest.save(photo);

        assertThat(result, is(photo));
    }

    @Test
    public void shouldDeletePhoto() {
        UUID photoId = UUID.randomUUID();
        when(photoDao.delete(photoId)).thenReturn(true);

        boolean result = underTest.delete(photoId);

        assertThat(result, is(true));
    }

    @Test
    public void shouldUpdatePhotoTitle() {
        UUID photoId = UUID.randomUUID();
        Photo photo = aPhoto().build();
        PhotoMetadata photoMetadata = createPhotoMetadata(photoId);
        String titleUpdate = "title";
        when(photoDao.updateFieldForId(photo.getPhotoId(), titleUpdate, photo.getTitle())).thenReturn(true);
        when(photoDao.findPhotoById(photoId)).thenReturn(photoMetadata);
        when(photoFactory.convert(photoMetadata)).thenReturn(photo);

        TitleEditRequest request = new TitleEditRequest(photoId, titleUpdate);
        Photo result = underTest.updateTitle(request);

        assertThat(result, is(photo));
    }

    @Test
    public void shouldUpdatePhotoDescription() {
        UUID photoId = UUID.randomUUID();
        PhotoMetadata photoMetadata = createPhotoMetadata(photoId);
        Photo photo = aPhoto().build();

        String descriptionUpdate = "title";
        when(photoDao.updateFieldForId(photo.getPhotoId(), "description", photo.getDescription())).thenReturn(true);
        when(photoDao.findPhotoById(photoId)).thenReturn(photoMetadata);
        when(photoFactory.convert(photoMetadata)).thenReturn(photo);

        Photo result = underTest.updateDescription(new DescriptionEditRequest(photoId, descriptionUpdate));

        assertThat(result, is(photo));
    }

    @Test
    public void shouldAddTag() {
        UUID photoId = UUID.randomUUID();
        PhotoMetadata photoMetadata = createPhotoMetadata(photoId);
        Photo photo = aPhoto().build();

        String tagToAdd = "tag1";
        when(photoDao.push(photo.getPhotoId(), TAGS, tagToAdd)).thenReturn(true);
        when(photoDao.findPhotoById(photoId)).thenReturn(photoMetadata);
        when(photoFactory.convert(photoMetadata)).thenReturn(photo);

        Photo result = underTest.addTag(new TagRequest(photoId, tagToAdd));

        assertThat(result, is(photo));
    }

    @Test
    public void shouldRemoveTag() {
        UUID photoId = UUID.randomUUID();
        PhotoMetadata photoMetadata = createPhotoMetadata(photoId);
        Photo photo = aPhoto().build();

        String tagToRemove = "tag1";
        when(photoDao.pull(photo.getPhotoId(), TAGS, tagToRemove)).thenReturn(true);
        when(photoDao.findPhotoById(photoId)).thenReturn(photoMetadata);
        when(photoFactory.convert(photoMetadata)).thenReturn(photo);

        Photo result = underTest.removeTag(new TagRequest(photoId, tagToRemove));

        assertThat(result, is(photo));
    }

    @Test
    public void canFindAllByCategory() {
        UUID photoId = UUID.randomUUID();
        PhotoMetadata photoMetadata = createPhotoMetadata(photoId);
        Photo photo = aPhoto().build();

        UUID categoryId = UUID.randomUUID();

        when(photoDao.removeCategory(photo.getPhotoId(), CATEGORIES, categoryId)).thenReturn(true);
        when(photoDao.findByCategoryId(categoryId)).thenReturn(List.of(photoMetadata));
        when(photoFactory.convert(photoMetadata)).thenReturn(photo);

        List<Photo> result = underTest.findAllByCategory(categoryId);

        assertThat(result, is(List.of(photo)));
    }

    @Test
    public void shouldRemoveCategory() {
        UUID photoId = UUID.randomUUID();
        PhotoMetadata photoMetadata = createPhotoMetadata(photoId);
        Photo photo = aPhoto().build();

        UUID categoryId = UUID.randomUUID();

        when(photoDao.removeCategory(photo.getPhotoId(), CATEGORIES, categoryId)).thenReturn(true);
        when(photoDao.findPhotoById(photoId)).thenReturn(photoMetadata);
        when(photoFactory.convert(photoMetadata)).thenReturn(photo);

        Photo result = underTest.removeCategory(new CategoryRequest(photoId, categoryId));

        assertThat(result, is(photo));
    }

    private PhotoMetadata createPhotoMetadata(UUID photoId) {
        return aPhotoMetadata().withPhotoId(photoId).build();
    }
}
