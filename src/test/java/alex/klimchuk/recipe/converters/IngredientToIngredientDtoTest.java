package alex.klimchuk.recipe.converters;

import alex.klimchuk.recipe.domain.Ingredient;
import alex.klimchuk.recipe.domain.Recipe;
import alex.klimchuk.recipe.domain.UnitOfMeasure;
import alex.klimchuk.recipe.dto.IngredientDto;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class IngredientToIngredientDtoTest {

    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "CheeseBurger";
    public static final Long ID_VALUE = 1L;
    public static final Long UOM_ID = 2L;

    IngredientToIngredientDto converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientToIngredientDto(new UnitOfMeasureToUnitOfMeasureDto());
    }

    @Test
    public void testNullConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void testConvertNullUOM() {
        Ingredient ingredient = Ingredient.builder()
                .id(ID_VALUE)
                .recipe(RECIPE)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .build();

        IngredientDto ingredientDto = converter.convert(ingredient);

        assertNull(Objects.requireNonNull(ingredientDto).getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredientDto.getId());
        assertEquals(AMOUNT, ingredientDto.getAmount());
        assertEquals(DESCRIPTION, ingredientDto.getDescription());
    }

    @Test
    public void testConvertWithUom() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);

        Ingredient ingredient = Ingredient.builder()
                .id(ID_VALUE)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .unitOfMeasure(unitOfMeasure)
                .build();

        IngredientDto ingredientDto = converter.convert(ingredient);

        assertEquals(ID_VALUE, Objects.requireNonNull(ingredientDto).getId());
        assertNotNull(ingredientDto.getUnitOfMeasure());
        assertEquals(UOM_ID, ingredientDto.getUnitOfMeasure().getId());
        assertEquals(AMOUNT, ingredientDto.getAmount());
        assertEquals(DESCRIPTION, ingredientDto.getDescription());
    }

}
