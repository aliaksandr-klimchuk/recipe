package alex.klimchuk.recipe.repositories;

import alex.klimchuk.recipe.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);

}
