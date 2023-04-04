package com.steve.gallery.gallerymanagementservice.adapter.rest.client;

import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDto;
import com.steve.gallery.gallerymanagementservice.adapter.rest.CategoryDtoBuilder;
import com.steve.gallery.gallerymanagementservice.domain.Category;
import com.steve.gallery.gallerymanagementservice.domain.CategoryBuilder;
import com.steve.gallery.gallerymanagementservice.domain.service.CategoryFinder;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategorySearchResourceTest {

    public static final UUID CATEGORY_ID_1 = UUID.randomUUID();
    public static final UUID CATEGORY_ID_2 = UUID.randomUUID();
    public static final UUID CATEGORY_ID_3 = UUID.randomUUID();
    private final CategoryFinder categoryFinder = mock(CategoryFinder.class);;
    private final CategoryDtoFactory categoryDtoFactory = mock(CategoryDtoFactory.class);
    private final CategorySearchResource underTest = new CategorySearchResource(categoryFinder, categoryDtoFactory);

    @Test
    public void canReturnAllCategories() {
        Category category1 = aCategory(CATEGORY_ID_1);
        Category category2 = aCategory(CATEGORY_ID_2);
        Category category3 = aCategory(CATEGORY_ID_3);
        CategoryDto categoryDto1 = aCategoryDto(CATEGORY_ID_1);
        CategoryDto categoryDto2 = aCategoryDto(CATEGORY_ID_2);
        CategoryDto categoryDto3 = aCategoryDto(CATEGORY_ID_3);
        List<Category> categories = List.of(category1, category2, category3);
        List<CategoryDto> categoryDtoList = List.of(categoryDto1, categoryDto2, categoryDto3);
        when(categoryFinder.findAll()).thenReturn(categories);

        when(categoryDtoFactory.convert(category1)).thenReturn(categoryDto1);
        when(categoryDtoFactory.convert(category2)).thenReturn(categoryDto2);
        when(categoryDtoFactory.convert(category3)).thenReturn(categoryDto3);

        ResponseEntity<List<CategoryDto>> result = underTest.categories();


        ResponseEntity<List<CategoryDto>> expected = ResponseEntity.ok(categoryDtoList);
        assertThat(result, is(expected));
    }

    private Category aCategory(UUID categoryId) {
        return new CategoryBuilder()
                .withCategoryId(categoryId)
                .withTitle("my title")
                .withSubtitle("a nice description of the category")
                .build();
    }


    private CategoryDto aCategoryDto(UUID categoryId) {
        return new CategoryDtoBuilder()
                .withCategoryId(categoryId)
                .withTitle("my title")
                .withSubtitle("a nice description of the category")
                .build();
    }

}