package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.dto.UnitOfMeasureDto;
import alex.klimchuk.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class UnitOfMeasureDtoToUnitOfMeasureTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = 1L;

    UnitOfMeasureDtoToUnitOfMeasure unitOfMeasureDtoConverter;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureDtoConverter = new UnitOfMeasureDtoToUnitOfMeasure();
    }

    @Test
    public void testNullObject() {
        assertNull(unitOfMeasureDtoConverter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(unitOfMeasureDtoConverter.convert(new UnitOfMeasureDto()));
    }

    @Test
    public void testConvert() {
        UnitOfMeasureDto unitOfMeasureDto = UnitOfMeasureDto.builder()
                .id(LONG_VALUE)
                .description(DESCRIPTION)
                .build();

        UnitOfMeasure unitOfMeasure = unitOfMeasureDtoConverter.convert(unitOfMeasureDto);

        assertNotNull(unitOfMeasure);
        assertEquals(LONG_VALUE, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
    }

}
