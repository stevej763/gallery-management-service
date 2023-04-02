package com.steve.gallery.gallerymanagementservice.configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoMetadataFactory;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoFactory;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.PhotoUploadRequestFactory;
import com.steve.gallery.gallerymanagementservice.adapter.s3.S3DeletionResource;
import com.steve.gallery.gallerymanagementservice.adapter.s3.S3UploadRequestFactory;
import com.steve.gallery.gallerymanagementservice.adapter.s3.S3UploadResource;
import com.steve.gallery.gallerymanagementservice.configuration.aws.S3ConfigurationContext;
import com.steve.gallery.gallerymanagementservice.domain.DeletionResource;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFactory;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoCreationService;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDeletionService;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDetailsEditor;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoFinder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationPropertiesScan("com.steve.gallery.gallerymanagementservice.configuration")
public class ResourceConfiguration {

    private final MongoTemplate mongoTemplate;
    private final S3ConfigurationContext s3Configuration;
    private final AmazonS3 s3Client;

    public ResourceConfiguration(MongoTemplate mongoTemplate,
                                 S3ConfigurationContext s3Configuration,
                                 AmazonS3 s3Client) {
        this.mongoTemplate = mongoTemplate;
        this.s3Configuration = s3Configuration;
        this.s3Client = s3Client;
    }

    @Bean
    PhotoUploadRequestFactory photoUploadRequestFactory() {
        return new PhotoUploadRequestFactory();
    }

    @Bean
    PhotoDtoFactory photoDtoFactory() {
        return new PhotoDtoFactory(s3Configuration.getEndpoint() + "/" + s3Configuration.getBucketName());
    }

    @Bean
    PhotoFinder photoFinder() {
        return new PhotoFinder(mongoPhotoRepository());
    }

    @Bean
    PhotoDetailsEditor photoDetailsEditor() {
        return new PhotoDetailsEditor(photoFinder(), mongoPhotoRepository());
    }

    @Bean
    PhotoCreationService photoCreationService() {
        S3UploadResource uploadResource = new S3UploadResource(s3Client, s3Configuration.getBucketName(), new S3UploadRequestFactory());
        return new PhotoCreationService(mongoPhotoRepository(), photoFactory(), photoDtoFactory(), uploadResource);
    }

    @Bean
    PhotoFactory photoFactory() {
        return new PhotoFactory();
    }

    @Bean
    PhotoDeletionService photoDeletionService() {
        DeletionResource deletionResource = new S3DeletionResource(s3Client, s3Configuration.getBucketName());
        return new PhotoDeletionService(mongoPhotoRepository(), deletionResource);
    }

    @Bean
    PhotoRepository mongoPhotoRepository() {
        PhotoDao photoDao = new PhotoDao(mongoTemplate);
        return new MongoPhotoRepository(photoDao, photoFactory(), new PhotoMetadataFactory());
    }
}
