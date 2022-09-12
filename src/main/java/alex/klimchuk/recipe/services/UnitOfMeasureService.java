package alex.klimchuk.recipe.services;

import alex.klimchuk.recipe.dto.UnitOfMeasureDto;

import java.util.Set;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public interface UnitOfMeasureService {

    Set<UnitOfMeasureDto> findAll();

}
