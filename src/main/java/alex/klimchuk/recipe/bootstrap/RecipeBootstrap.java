package alex.klimchuk.recipe.bootstrap;

import alex.klimchuk.recipe.domain.*;
import alex.klimchuk.recipe.exceptions.NotFoundException;
import alex.klimchuk.recipe.repositories.CategoryRepository;
import alex.klimchuk.recipe.repositories.RecipeRepository;
import alex.klimchuk.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public RecipeBootstrap(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository,
                           RecipeRepository recipeRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        cleanData();
        loadCategories();
        loadUnitOfMeasures();
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading Bootstrap Data");
    }

    public void cleanData() {
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
    }

    private void loadCategories() {
        Category category1 = new Category();
        category1.setDescription("American");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setDescription("Italian");
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setDescription("Mexican");
        categoryRepository.save(category3);

        Category category4 = new Category();
        category4.setDescription("Fast Food");
        categoryRepository.save(category4);
    }

    private void loadUnitOfMeasures() {
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setDescription("TeaSpoon");
        unitOfMeasureRepository.save(unitOfMeasure1);

        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setDescription("TableSpoon");
        unitOfMeasureRepository.save(unitOfMeasure2);

        UnitOfMeasure unitOfMeasure3 = new UnitOfMeasure();
        unitOfMeasure3.setDescription("Cup");
        unitOfMeasureRepository.save(unitOfMeasure3);

        UnitOfMeasure unitOfMeasure4 = new UnitOfMeasure();
        unitOfMeasure4.setDescription("Pinch");
        unitOfMeasureRepository.save(unitOfMeasure4);

        UnitOfMeasure unitOfMeasure5 = new UnitOfMeasure();
        unitOfMeasure5.setDescription("Ounce");
        unitOfMeasureRepository.save(unitOfMeasure5);

        UnitOfMeasure unitOfMeasure6 = new UnitOfMeasure();
        unitOfMeasure6.setDescription("Each");
        unitOfMeasureRepository.save(unitOfMeasure6);

        UnitOfMeasure unitOfMeasure7 = new UnitOfMeasure();
        unitOfMeasure7.setDescription("Pint");
        unitOfMeasureRepository.save(unitOfMeasure7);

        UnitOfMeasure unitOfMeasure8 = new UnitOfMeasure();
        unitOfMeasure8.setDescription("Dash");
        unitOfMeasureRepository.save(unitOfMeasure8);
    }

    private Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new LinkedHashSet<>();

        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if (eachUomOptional.isEmpty()) {
            throw new NotFoundException("Each UnitOfMeasure Not Found!");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("TableSpoon");

        if (tableSpoonUomOptional.isEmpty()) {
            throw new NotFoundException("TableSpoon UnitOfMeasure Not Found!");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("TeaSpoon");

        if (teaSpoonUomOptional.isEmpty()) {
            throw new NotFoundException("TeaSpoon UnitOfMeasure Not Found!");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if (dashUomOptional.isEmpty()) {
            throw new NotFoundException("Dash UnitOfMeasure Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if (pintUomOptional.isEmpty()) {
            throw new NotFoundException("Pint UnitOfMeasure Not Found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        if (cupsUomOptional.isEmpty()) {
            throw new NotFoundException("Cups UnitOfMeasure Not Found");
        }

        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();

        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if (americanCategoryOptional.isEmpty()) {
            throw new NotFoundException("American Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if (mexicanCategoryOptional.isEmpty()) {
            throw new NotFoundException("Mexican Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        Recipe guacamoleRecipe = new Recipe();
        guacamoleRecipe.setDescription("Perfect Guacamole");
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setDifficulty(Difficulty.EASY);
        guacamoleRecipe.setDirections("""
                1. Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the
                avocado with a blunt knife and scoop out the flesh with a spoon
                2. Mash with a fork: Using a fork, roughly mash the avocado.
                (Don't overdo it! The guacamole should be a little chunky.)
                3. Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice.
                The acid in the lime juice will provide some balance to the richness of the avocado and
                will help delay the avocados from turning brown. Add the chopped onion, cilantro, black pepper,
                and chilies. Chili peppers vary individually in their hotness. So, start with a half of a chili
                pepper and add to the guacamole to your desired degree of hotness. Remember that much of this
                is done to taste because of the variability in the fresh ingredients.
                Start with this recipe and adjust to your taste.
                4. Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole
                cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn
                the guacamole brown.) Refrigerate until ready to serve.
                Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole,
                add it just before serving.

                Read more: https://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd
                """);

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("""
                For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.
                Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it
                (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.
                The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability
                of other ingredients stop you from making guacamole.
                To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip.
                Purists may be horrified, but so what? It tastes great.

                Read more: https://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws
                """);
        guacamoleRecipe.setNotes(guacamoleNotes);

        guacamoleRecipe.addIngredient(new Ingredient("Ripe avocados", 2.0, eachUom))
                .addIngredient(new Ingredient("Kosher salt", 0.5, teaSpoonUom))
                .addIngredient(new Ingredient("Fresh lime juice or lemon juice", 2.0, tableSpoonUom))
                .addIngredient(new Ingredient("Minced red onion or thinly sliced green onion", 2.0, tableSpoonUom))
                .addIngredient(new Ingredient("Serrano chilies, stems and seeds removed, minced", 2.0, eachUom))
                .addIngredient(new Ingredient("Cilantro", 2.0, tableSpoonUom))
                .addIngredient(new Ingredient("Freshly grated black pepper", 2.0, dashUom))
                .addIngredient(new Ingredient("Ripe tomato, seeds and pulp removed, chopped", 0.5, eachUom));

        guacamoleRecipe.getCategories().add(americanCategory);
        guacamoleRecipe.getCategories().add(mexicanCategory);

        guacamoleRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setSource("Simple Recipes");

        recipes.add(guacamoleRecipe);

        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("""
                1. Prepare a gas or charcoal grill for medium-high, direct heat.
                2. Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano,
                cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make
                a loose paste. Add the chicken to the bowl and toss to coat all over.
                Set aside to marinate while the grill heats and you prepare the rest of the toppings.
                3. Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted
                into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.
                4. Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat.
                As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat
                for a few seconds on the other side.
                Wrap warmed tortillas in a tea towel to keep them warm until serving.
                5. Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful
                of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices.
                Drizzle with the thinned sour cream. Serve with lime wedges.

                Read more: https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm
                """);

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("""
                We have a family motto and it is this: Everything goes better in a tortilla.
                Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled
                jalapenos. I can always sniff out a late-night slacker when the aroma of tortillas heating in a hot pan
                on the stove comes wafting through the house.
                Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!
                First, I marinate the chicken briefly in a spicy paste of anchor chile powder, oregano, cumin, and sweet
                orange juice while the grill is heating. You can also use this time to prepare the taco toppings.
                Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble
                the tacos and dig in. The whole meal comes together in about 30 minutes!

                Read more: https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ
                """);
        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe.addIngredient(new Ingredient("Anchor Chili Powder", 2.0, tableSpoonUom))
                .addIngredient(new Ingredient("Dried Oregano", 1.0, teaSpoonUom))
                .addIngredient(new Ingredient("Dried Cumin", 1.0, teaSpoonUom))
                .addIngredient(new Ingredient("Sugar", 1.0, teaSpoonUom))
                .addIngredient(new Ingredient("Salt", 0.5, teaSpoonUom))
                .addIngredient(new Ingredient("Clove of Garlic, Chopped", 1.0, eachUom))
                .addIngredient(new Ingredient("Finely grated orange zest", 1.0, tableSpoonUom))
                .addIngredient(new Ingredient("Fresh-squeezed orange juice", 3.0, tableSpoonUom))
                .addIngredient(new Ingredient("Olive Oil", 2.0, tableSpoonUom))
                .addIngredient(new Ingredient("Boneless chicken thighs", 4.0, tableSpoonUom))
                .addIngredient(new Ingredient("Small corn tortillas", 8.0, eachUom))
                .addIngredient(new Ingredient("Packed baby arugula", 3.0, cupsUom))
                .addIngredient(new Ingredient("Medium ripe avocados, slice", 2.0, eachUom))
                .addIngredient(new Ingredient("Radishes, thinly sliced", 4.0, eachUom))
                .addIngredient(new Ingredient("Cherry tomatoes, halved", 0.5, pintUom))
                .addIngredient(new Ingredient("Red onion, thinly sliced", 0.25, eachUom))
                .addIngredient(new Ingredient("Roughly chopped cilantro", 4.0, eachUom))
                .addIngredient(new Ingredient("Cup sour cream thinned with 1/4 cup milk", 4.0, cupsUom))
                .addIngredient(new Ingredient("Lime, cut into wedges", 4.0, eachUom));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        tacosRecipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacosRecipe.setServings(4);
        tacosRecipe.setSource("Simple Recipes");

        recipes.add(tacosRecipe);

        return recipes;
    }

}
