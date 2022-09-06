package alex.klimchuk.recipe.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum Difficulty {

    EASY(0L, "Easy"),
    MODERATE(1L, "Moderate"),
    HARD(2L, "Hard");

    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

}
