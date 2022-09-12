package alex.klimchuk.recipe.services.impl;

import alex.klimchuk.recipe.dto.UnitOfMeasureDto;
import alex.klimchuk.recipe.converters.UnitOfMeasureToUnitOfMeasureDto;
import alex.klimchuk.recipe.repositories.UnitOfMeasureRepository;
import alex.klimchuk.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureDto = unitOfMeasureToUnitOfMeasureDto;
    }

    @Override
    public Set<UnitOfMeasureDto> findAll() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                        .spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureDto::convert)
                .collect(Collectors.toSet());
    }

}
