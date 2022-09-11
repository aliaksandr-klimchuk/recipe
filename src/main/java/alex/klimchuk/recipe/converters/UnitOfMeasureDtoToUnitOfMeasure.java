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
public class UnitOfMeasureDtoToUnitOfMeasure implements Converter<UnitOfMeasureDto, UnitOfMeasure> {

    @Override
    @Nullable
    @Synchronized
    public UnitOfMeasure convert(UnitOfMeasureDto unitOfMeasureDto) {
        return UnitOfMeasure.builder()
                .id(unitOfMeasureDto.getId())
                .description(unitOfMeasureDto.getDescription())
                .build();
    }

}
