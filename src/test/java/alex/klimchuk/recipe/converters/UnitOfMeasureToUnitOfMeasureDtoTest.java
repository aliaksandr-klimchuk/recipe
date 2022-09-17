package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.domain.UnitOfMeasure;
import alex.klimchuk.recipe.dto.UnitOfMeasureDto;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class UnitOfMeasureToUnitOfMeasureDtoTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = 1L;

    UnitOfMeasureToUnitOfMeasureDto unitOfMeasureConverter;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureConverter = new UnitOfMeasureToUnitOfMeasureDto();
    }

    @Test
    public void testNullObject() {
        assertNull(unitOfMeasureConverter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(unitOfMeasureConverter.convert(new UnitOfMeasure()));
    }

    @Test
    public void testConvert() {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
                .id(LONG_VALUE)
                .description(DESCRIPTION)
                .build();

        UnitOfMeasureDto unitOfMeasureDto = unitOfMeasureConverter.convert(unitOfMeasure);

        assertEquals(LONG_VALUE, Objects.requireNonNull(unitOfMeasureDto).getId());
        assertEquals(DESCRIPTION, unitOfMeasureDto.getDescription());
    }

}
