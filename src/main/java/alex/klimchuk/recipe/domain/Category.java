package alex.klimchuk.recipe.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Data
@Entity
@Builder
@ToString
@EqualsAndHashCode(exclude = {"recipes"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
