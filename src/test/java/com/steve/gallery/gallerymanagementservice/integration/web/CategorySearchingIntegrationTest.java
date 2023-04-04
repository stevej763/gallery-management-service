package com.steve.gallery.gallerymanagementservice.integration.web;

import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryMetadata;
import com.steve.gallery.gallerymanagementservice.adapter.repository.mongo.CategoryMetadataBuilder;
import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDtoBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CategorySearchingIntegrationTest extends BaseWebIntegrationTest {

    @Test
    public void searchAllShouldReturnAllCategories() {
        String category1 = "category1";
        String category2 = "category2";
        String category3 = "category3";
        UUID categoryId1 = UUID.randomUUID();
        UUID categoryId2 = UUID.randomUUID();
        UUID categoryId3 = UUID.randomUUID();
        saveCategoryToDatabase(createCategoryMetadata(category1, categoryId1));
        saveCategoryToDatabase(createCategoryMetadata(category2, categoryId2));
        saveCategoryToDatabase(createCategoryMetadata(category3, categoryId3));

        ResponseEntity<CategoryDto[]> result = restTemplate.exchange(
                getCategoryBasePath(),
                HttpMethod.GET,
                null,
                CategoryDto[].class);

        CategoryDto categoryDto1 = aCategoryDto(category1, categoryId1);
        CategoryDto categoryDto2 = aCategoryDto(category2, categoryId2);
        CategoryDto categoryDto3 = aCategoryDto(category3, categoryId3);

        List<CategoryDto> resultList = Arrays.asList(result.getBody());
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertThat(resultList.size(), is(3));
        assertThat(reflectionEquals(resultList.get(0), categoryDto1, "createdAt", "modifiedAt"), is(true));
        assertThat(reflectionEquals(resultList.get(1), categoryDto2, "createdAt", "modifiedAt"), is(true));
        assertThat(reflectionEquals(resultList.get(2), categoryDto3, "createdAt", "modifiedAt"), is(true));
    }

    private CategoryDto aCategoryDto(String title, UUID categoryId1) {
        return new CategoryDtoBuilder()
                .withCategoryId(categoryId1)
                .withTitle(title)
                .withSubtitle("a nice description of the category").build();
    }

    private CategoryMetadata createCategoryMetadata(String title, UUID categoryId) {
        return new CategoryMetadataBuilder()
                .withCategoryId(categoryId)
                .withTitle(title)
                .withSubtitle("a nice description of the category")
                .build();
    }
}
