package com.steve.gallery.gallerymanagementservice.configuration;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.MongoPhotoRepository;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.PhotoDao;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFinder;
import com.steve.gallery.gallerymanagementservice.domain.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class ResourceConfiguration {

    @Autowired
    MongoTemplate mongoTemplate;

    @Bean
    PhotoFinder photoFinder() {
        return new PhotoFinder(mongoPhotoRepository());
    }

    @Bean
    PhotoRepository mongoPhotoRepository() {
        PhotoDao photoDao = new PhotoDao(mongoTemplate);
        return new MongoPhotoRepository(photoDao);
    }
}
