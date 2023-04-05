package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.CategoryCreationRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CreateCategoryIntegrationTest extends BaseWebIntegrationTest {

    @Test
    public void canCreateCategory() {
        String categoryTitle = "title";
        String categorySubtitle = "subtitle";

        String url = getCategoryBasePath() + "/create";
        CategoryCreationRequestDto request = new CategoryCreationRequestDto(categoryTitle, categorySubtitle);

        ResponseEntity<CategoryDto> response = restTemplate.postForEntity(url, request, CategoryDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getTitle(), is(categoryTitle));
        assertThat(response.getBody().getSubtitle(), is(categorySubtitle));
    }

}
