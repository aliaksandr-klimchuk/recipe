package alex.klimchuk.recipe.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Data
@Entity
@Builder
@ToString
@EqualsAndHashCode(exclude = {"recipe"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Recipe recipe;

    @Lob
    @Column(name = "recipe_notes")
    private String recipeNotes;

}
