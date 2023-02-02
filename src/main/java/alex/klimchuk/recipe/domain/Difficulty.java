package alex.klimchuk.recipe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;

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
