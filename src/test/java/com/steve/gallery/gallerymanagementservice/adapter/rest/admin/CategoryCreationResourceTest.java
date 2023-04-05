package com.steve.gallery.gallerymanagementservice.adapter.rest.admin;

import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDtoBuilder;
import com.steve.gallery.gallerymanagementservice.adapter.rest.admin.resource.CategoryCreationResource;
import com.steve.gallery.gallerymanagementservice.adapter.rest.client.CategoryDtoFactory;
import com.steve.gallery.gallerymanagementservice.domain.Category;
import com.steve.gallery.gallerymanagementservice.domain.CategoryCreationRequest;
import com.steve.gallery.gallerymanagementservice.domain.service.CategoryCreator;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static com.steve.gallery.gallerymanagementservice.domain.CategoryBuilder.aCategory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryCreationResourceTest {

    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    private final CategoryDtoFactory categoryDtoFactory = mock(CategoryDtoFactory.class);
    private final CategoryCreator categoryCreator = mock(CategoryCreator.class);
    private final CategoryCreationResource underTest = new CategoryCreationResource(categoryCreator, categoryDtoFactory);

    @Test
    public void canCreateNewCategory() {
        Category category = aCategory().build();
        CategoryDto categoryDto = new CategoryDtoBuilder().build();
        when(categoryCreator.save(new CategoryCreationRequest(TITLE, SUBTITLE))).thenReturn(category);
        when(categoryDtoFactory.convert(category)).thenReturn(categoryDto);

        ResponseEntity<CategoryDto> result = underTest.create(new CategoryCreationRequestDto(TITLE, SUBTITLE));
        assertThat(result, is(ResponseEntity.ok(categoryDto)));
    }

}