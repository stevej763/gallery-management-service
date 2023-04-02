package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.PhotoDtoFactory;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.DescriptionEditRequestDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.update.TitleEditRequestDto;
import com.steve.gallery.gallerymanagementservice.domain.DescriptionEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.Photo;
import com.steve.gallery.gallerymanagementservice.domain.TitleEditRequest;
import com.steve.gallery.gallerymanagementservice.domain.service.PhotoDetailsEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/edit")
public class PhotoDetailsModificationResource {

    private final PhotoDetailsEditor photoDetailsEditor;
    private final PhotoDtoFactory photoDtoFactory;

    public PhotoDetailsModificationResource(PhotoDetailsEditor photoDetailsEditor, PhotoDtoFactory photoDtoFactory) {
        this.photoDetailsEditor = photoDetailsEditor;
        this.photoDtoFactory = photoDtoFactory;
    }

    @PostMapping(value = "{photoId}/title", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoDto> editTitle(@RequestBody TitleEditRequestDto titleEditRequestDto) {
        TitleEditRequest titleEditRequest = new TitleEditRequest(titleEditRequestDto.getPhotoId(), titleEditRequestDto.getTitleChange());
        Photo photo = photoDetailsEditor.editTitle(titleEditRequest);
        PhotoDto photoDto = photoDtoFactory.convert(photo);
        return ResponseEntity.ok(photoDto);
    }

    @PostMapping(value = "{photoId}/description", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoDto> editDescription(@RequestBody DescriptionEditRequestDto descriptionEditRequestDto) {
        DescriptionEditRequest editRequest = new DescriptionEditRequest(descriptionEditRequestDto.getPhotoId(), descriptionEditRequestDto.getDescriptionChange());
        Photo photo = photoDetailsEditor.editDescription(editRequest);
        PhotoDto photoDto = photoDtoFactory.convert(photo);
        return ResponseEntity.ok(photoDto);
    }
}
