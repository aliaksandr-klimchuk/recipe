package alex.klimchuk.recipe.domain;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
public enum Difficulty {

    EASY(0L,"Easy"),
    MODERATE(1L,"Moderate"),
    HARD(2L,"Hard");

    private Long id;

    private String name;

    Difficulty() {

    }

    Difficulty(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
