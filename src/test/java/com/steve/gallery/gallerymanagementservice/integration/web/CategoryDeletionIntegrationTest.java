package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryMetadata;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.CategoryDeletionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryMetadataBuilder.aCategoryMetadata;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpMethod.DELETE;

public class CategoryDeletionIntegrationTest extends BaseWebIntegrationTest {

    @Test
    public void canDeleteACategory() {
        UUID categoryId = UUID.randomUUID();
        CategoryMetadata category = aCategoryMetadata().withCategoryId(categoryId).build();
        saveCategoryToDatabase(category);

        String deletionUrl = getCategoryAdminBasePath() + "/delete/" + categoryId;
        ResponseEntity<CategoryDeletionResponseDto> response = restTemplate.exchange(deletionUrl, DELETE, null, CategoryDeletionResponseDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new CategoryDeletionResponseDto(true)));
    }

}
