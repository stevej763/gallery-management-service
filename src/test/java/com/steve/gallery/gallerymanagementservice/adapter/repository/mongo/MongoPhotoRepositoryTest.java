package com.steve.gallery.gallerymanagementservice.adapter.repository.mongo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoBuilder;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class MongoPhotoRepositoryTest {

  private final PhotoDao photoDao = mock(PhotoDao.class);
  private final MongoPhotoRepository underTest = new MongoPhotoRepository(photoDao);

  @Test
  public void shouldFindAllPhotos() {
    Photo photo = new PhotoBuilder().build();
    when(photoDao.findAllPhotos()).thenReturn(List.of(photo));

    List<Photo> result = underTest.findAll();

    List<Photo> expected = List.of(photo);
    assertThat(result, is(expected));
  }

  @Test
  public void shouldFindPhotoById() {
    UUID photoId = UUID.randomUUID();
    Photo photo = new PhotoBuilder().withPhotoId(photoId).build();
    when(photoDao.findPhotoById(photoId)).thenReturn(photo);

    Photo result = underTest.findById(photoId);

    assertThat(result, is(photo));
  }
}
