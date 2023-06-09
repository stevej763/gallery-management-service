package com.steve.gallery.gallerymanagementservice.configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.*;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoFactory;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.PhotoUploadRequestFactory;
import com.steve.gallery.gallerymanagementservice.adapter.rest.client.CategoryDtoFactory;
import com.steve.gallery.gallerymanagementservice.adapter.s3.S3DeletionResource;
import com.steve.gallery.gallerymanagementservice.adapter.s3.S3UploadRequestFactory;
import com.steve.gallery.gallerymanagementservice.adapter.s3.S3UploadResource;
import com.steve.gallery.gallerymanagementservice.configuration.aws.S3ConfigurationContext;
import com.steve.gallery.gallerymanagementservice.domain.adater.CategoryRepository;
import com.steve.gallery.gallerymanagementservice.domain.adater.DeletionResource;
import com.steve.gallery.gallerymanagementservice.domain.adater.PhotoRepository;
import com.steve.gallery.gallerymanagementservice.domain.photo.PhotoFactory;
import com.steve.gallery.gallerymanagementservice.domain.service.*;
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
    CategoryDtoFactory categoryDtoFactory() {
        return new CategoryDtoFactory();
    }


    @Bean
    PhotoFinder photoFinder() {
        return new PhotoFinder(mongoPhotoRepository());
    }

    @Bean
    CategoryFinder categoryFinder() {
        return new CategoryFinder(mongoCategoryRepository());
    }

    @Bean
    CategoryCreator categoryCreator() {
        return new CategoryCreator(mongoCategoryRepository(), categoryFactory());
    }

    @Bean
    CategoryDeleter categoryDeleter() {
        return new CategoryDeleter(mongoCategoryRepository(), photoDetailsEditor());
    }

    @Bean
    PhotoDetailsEditor photoDetailsEditor() {
        return new PhotoDetailsEditor(photoFinder(), mongoPhotoRepository());
    }

    @Bean
    PhotoCreator photoCreationService() {
        S3UploadResource uploadResource = new S3UploadResource(s3Client, s3Configuration.getBucketName(), new S3UploadRequestFactory());
        return new PhotoCreator(mongoPhotoRepository(), photoFactory(), photoDtoFactory(), uploadResource);
    }

    @Bean
    PhotoFactory photoFactory() {
        return new PhotoFactory();
    }

    @Bean
    PhotoDeleter photoDeletionService() {
        DeletionResource deletionResource = new S3DeletionResource(s3Client, s3Configuration.getBucketName());
        return new PhotoDeleter(mongoPhotoRepository(), deletionResource);
    }

    @Bean
    PhotoRepository mongoPhotoRepository() {
        PhotoDao photoDao = new PhotoDao(mongoTemplate);
        return new MongoPhotoRepository(photoDao, photoFactory(), new PhotoMetadataFactory());
    }

    @Bean
    CategoryRepository mongoCategoryRepository() {
        CategoryDao categoryRepository = new CategoryDao(mongoTemplate);
        return new MongoCategoryRepository(categoryRepository, categoryFactory(), new CategoryMetadataFactory());
    }

    @Bean
    CategoryFactory categoryFactory() {
        return new CategoryFactory();
    }
}
