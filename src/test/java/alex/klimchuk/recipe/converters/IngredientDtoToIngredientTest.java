package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.dto.IngredientDto;
import alex.klimchuk.recipe.dto.UnitOfMeasureDto;
import alex.klimchuk.recipe.domain.Ingredient;
import alex.klimchuk.recipe.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class IngredientDtoToIngredientTest {

    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "CheeseBurger";
    public static final Long ID_VALUE = 1L;
    public static final Long UOM_ID = 2L;

    IngredientDtoToIngredient converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientDtoToIngredient(new UnitOfMeasureDtoToUnitOfMeasure());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientDto()));
    }

    @Test
    public void testConvert() {
        UnitOfMeasureDto unitOfMeasureDto = new UnitOfMeasureDto();
        unitOfMeasureDto.setId(UOM_ID);

        IngredientDto ingredientDto = IngredientDto.builder()
                .id(ID_VALUE)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .unitOfMeasure(unitOfMeasureDto)
                .build();

        Ingredient ingredient = converter.convert(ingredientDto);

        assertNotNull(ingredient);
        assertNotNull(ingredient.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
    }

    @Test
    public void testConvertWithNullUOM() {
        IngredientDto ingredientDto = IngredientDto.builder()
                .id(ID_VALUE)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .build();

        Ingredient ingredient = converter.convert(ingredientDto);

        assertNotNull(ingredient);
        assertNull(ingredient.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
    }

}
