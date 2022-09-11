package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.dto.UnitOfMeasureDto;
import alex.klimchuk.recipe.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Component
public class UnitOfMeasureToUnitOfMeasureDto implements Converter<UnitOfMeasure, UnitOfMeasureDto> {

    @Override
    @Nullable
    @Synchronized
    public UnitOfMeasureDto convert(UnitOfMeasure unitOfMeasure) {
        return UnitOfMeasureDto.builder()
                .id(unitOfMeasure.getId())
                .description(unitOfMeasure.getDescription())
                .build();
    }

}
