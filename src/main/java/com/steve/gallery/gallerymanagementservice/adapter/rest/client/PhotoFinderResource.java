package com.steve.gallery.gallerymanagementservice.adapter.rest.client;

import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.PhotoFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/gallery")
public class PhotoFinderResource {

    Logger LOGGER = LoggerFactory.getLogger(PhotoFinderResource.class);

    private final PhotoFinder photoFinder;

    public PhotoFinderResource(PhotoFinder photoFinder) {
        this.photoFinder = photoFinder;
    }

    @GetMapping
    public ResponseEntity<List<PhotoDto>> photos() {
        List<Photo> photos = photoFinder.findAll();
        List<PhotoDto> photoDtoList = photos.stream().map(photo -> new PhotoDto(photo.getPhotoId(), photo.getTitle())).toList();
        return ResponseEntity.ok(photoDtoList);
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoDto> findPhotoById(@PathVariable("photoId") UUID photoId) {
        Photo photo = photoFinder.findPhotoById(photoId);
        if (photo == null) {
            LOGGER.error("Search made for invalid id={}", photoId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PhotoDto(photo.getPhotoId(), photo.getTitle()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PhotoDto>> findPhotoByTitle(@RequestParam("title") String title) {
        List<Photo> photos = photoFinder.findPhotoByTitle(title);
        List<PhotoDto> photoDtoList = photos.stream().map(photo -> new PhotoDtoBuilder()
                .withPhotoId(photo.getPhotoId())
                .withTitle(photo.getTitle())
                .build()).toList();
        return ResponseEntity.ok(photoDtoList);
    }
}
