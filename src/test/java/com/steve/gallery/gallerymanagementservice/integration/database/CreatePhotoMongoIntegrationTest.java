package com.steve.gallery.gallerymanagementservice.integration.database;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadataFactory;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoFactory;
import com.steve.gallery.gallerymanagementservice.adapter.s3.S3UploadRequestFactory;
import com.steve.gallery.gallerymanagementservice.adapter.s3.S3UploadResource;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFactory;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequest;
import com.steve.gallery.gallerymanagementservice.domain.PhotoUploadRequestBuilder;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoCreationService;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoFinder;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreatePhotoMongoIntegrationTest extends BaseMongoIntegrationTest {

    @Test
    public void canCreateAPhotoThenRetrieveItFromTheDatabase() throws IOException {
        PhotoDao photoDao = new PhotoDao(mongoTemplate);
        MongoPhotoRepository photoRepository = new MongoPhotoRepository(photoDao, new PhotoFactory(), new PhotoMetadataFactory());
        PhotoFinder photoFinder = new PhotoFinder(photoRepository);

        MockMultipartFile uploadedFile = new MockMultipartFile("test", "originalFileName", "jpeg", "content".getBytes());

        File photo = new File(uploadedFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(photo);
        fileOutputStream.write(uploadedFile.getBytes());
        fileOutputStream.close();
        photo.deleteOnExit();

        S3UploadRequestFactory s3UploadRequestFactory = new S3UploadRequestFactory();
        PhotoCreationService photoCreationService = new PhotoCreationService(
                photoRepository,
                new PhotoFactory(),
                new PhotoDtoFactory("baseUrl"),
                new S3UploadResource(s3Client, BUCKET_NAME, s3UploadRequestFactory));

        String title = "title";
        String description = "description";
        List<String> tags = List.of(UUID.randomUUID().toString());
        List<UUID> categories = List.of(UUID.randomUUID());
        PhotoUploadRequest photoUploadRequest = new PhotoUploadRequestBuilder()
                .withTitle(title)
                .withDescription(description)
                .withTags(tags)
                .withCategories(categories)
                .withPhoto(photo)
                .build();
        photoCreationService.create(photoUploadRequest);

        List<Photo> results = photoFinder.findAll();

        assertThat(results.size(), is(1));
        Photo result = results.get(0);
        assertThat(result.getTitle(), is(title));
        assertThat(result.getDescription(), is(description));
        assertThat(result.getTags(), is(tags));
        assertThat(result.getCategories(), is(categories));
    }
}
