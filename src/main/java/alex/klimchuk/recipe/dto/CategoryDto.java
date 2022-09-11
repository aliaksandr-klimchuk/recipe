package alex.klimchuk.recipe.dto;

import lombok.*;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    private String description;

}
