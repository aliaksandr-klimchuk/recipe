package alex.klimchuk.recipe.services;

import alex.klimchuk.recipe.dto.UnitOfMeasureDto;
import alex.klimchuk.recipe.converters.UnitOfMeasureToUnitOfMeasureDto;
import alex.klimchuk.recipe.domain.UnitOfMeasure;
import alex.klimchuk.recipe.repositories.UnitOfMeasureRepository;
import alex.klimchuk.recipe.services.impl.UnitOfMeasureServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureDto unitOfMeasureToUnitOfMeasureDto;
    UnitOfMeasureService service;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        unitOfMeasureToUnitOfMeasureDto = new UnitOfMeasureToUnitOfMeasureDto();
        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureDto);
    }

    @Test
    public void testFindAll() {
        Set<UnitOfMeasure> unitOfMeasuresMock = new HashSet<>();
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(1L);
        unitOfMeasuresMock.add(unitOfMeasure1);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId(2L);
        unitOfMeasuresMock.add(unitOfMeasure2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasuresMock);

        Set<UnitOfMeasureDto> unitOfMeasures = service.findAll();

        assertEquals(2, unitOfMeasures.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }

}
