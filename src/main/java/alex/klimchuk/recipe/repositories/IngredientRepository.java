package alex.klimchuk.recipe.repositories;

import alex.klimchuk.recipe.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
