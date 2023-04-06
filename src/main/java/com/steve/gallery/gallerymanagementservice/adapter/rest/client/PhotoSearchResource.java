package com.steve.gallery.gallerymanagementservice.adapter.rest.client;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoFactory;
import com.steve.gallery.gallerymanagementservice.domain.photo.Photo;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v1/gallery", produces = APPLICATION_JSON_VALUE, consumes = ALL_VALUE)
public class PhotoSearchResource {

    private final Logger LOGGER = LoggerFactory.getLogger(PhotoSearchResource.class);

    private final PhotoFinder photoFinder;
    private final PhotoDtoFactory photoDtoFactory;

    public PhotoSearchResource(PhotoFinder photoFinder, PhotoDtoFactory photoDtoFactory) {
        this.photoFinder = photoFinder;
        this.photoDtoFactory = photoDtoFactory;
    }

    @GetMapping
    public ResponseEntity<List<PhotoDto>> photos() {
        List<Photo> photos = photoFinder.findAll();
        List<PhotoDto> photoDtoList = photos
                .stream()
                .map(photoDtoFactory::convert)
                .toList();
        return ResponseEntity.ok(photoDtoList);
    }

    @GetMapping(value = "/{photoId}")
    public ResponseEntity<PhotoDto> findPhotoById(@PathVariable("photoId") UUID photoId) {
        Photo photo = photoFinder.findPhotoById(photoId);
        if (photo == null) {
            LOGGER.error("Search made for invalid id={}", photoId);
            return ResponseEntity.notFound().build();
        }
        PhotoDto photoDto = photoDtoFactory.convert(photo);
        return ResponseEntity.ok(photoDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PhotoDto>> findPhotoByTitle(@RequestParam("title") String title) {
        List<Photo> photos = photoFinder.findPhotoByTitle(title);
        List<PhotoDto> photoDtoList = photos
                .stream()
                .map(photoDtoFactory::convert)
                .toList();
        return ResponseEntity.ok(photoDtoList);
    }
}
