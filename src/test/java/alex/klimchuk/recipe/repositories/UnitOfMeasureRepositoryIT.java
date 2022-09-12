package alex.klimchuk.recipe.repositories;

import alex.klimchuk.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@DataJpaTest
@RunWith(SpringRunner.class)
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    public void setUp() {

    }

    @Test
    //@DirtiesContext
    public void testFindByDescription() {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Cup");

        assertEquals("Cup", unitOfMeasureOptional.get().getDescription());
    }

    @Test
    public void testFindByDescriptionTeaSpoon() {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("TeaSpoon");

        assertEquals("TeaSpoon", unitOfMeasureOptional.get().getDescription());
    }

}
