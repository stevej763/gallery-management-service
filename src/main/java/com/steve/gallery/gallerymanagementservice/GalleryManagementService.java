package com.steve.gallery.gallerymanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.vault.config.VaultAutoConfiguration;

@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        VaultAutoConfiguration.class
})
public class GalleryManagementService {

    public static void main(String[] args) {
        SpringApplication.run(GalleryManagementService.class);
    }
}
