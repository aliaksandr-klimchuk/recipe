package alex.klimchuk.recipe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    public void testGetId() {
        Long id = 4L;
        category.setId(id);

        assertEquals(id, category.getId());
    }

    @Test
    public void testGetDescription() {

    }

    @Test
    public void testGetRecipes() {

    }

}
