package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.dto.CategoryDto;
import alex.klimchuk.recipe.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Component
public class CategoryToCategoryDto implements Converter<Category, CategoryDto> {

    @Override
    @Nullable
    @Synchronized
    public CategoryDto convert(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .description(category.getDescription())
                .build();
    }

}
