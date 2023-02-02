package alex.klimchuk.recipe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Double amount;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;

    @ManyToOne
    private Recipe recipe;

    public Ingredient(String description, Double amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

}
