package alex.klimchuk.recipe.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long id = 4L;
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    void getDescription() {

    }

    @Test
    void getRecipes() {

    }

}