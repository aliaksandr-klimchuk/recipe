package alex.klimchuk.recipe.bootstrap;

import alex.klimchuk.recipe.domain.*;
import alex.klimchuk.recipe.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
        recipeRepository.saveAll(getRecipes());
    }

    private Set<Recipe> getRecipes(){

        Set<Recipe> recipes = new LinkedHashSet<>();

        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if(eachUomOptional.isEmpty()){
            throw new RuntimeException("Each UOM Not Found!");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("TableSpoon");

        if(tableSpoonUomOptional.isEmpty()){
            throw new RuntimeException("TableSpoon UOM Not Found!");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("TeaSpoon");

        if(teaSpoonUomOptional.isEmpty()){
            throw new RuntimeException("TeaSpoon UOM Not Found!");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if(dashUomOptional.isEmpty()){
            throw new RuntimeException("Dash UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if(pintUomOptional.isEmpty()){
            throw new RuntimeException("Pint UOM Not Found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        if(cupsUomOptional.isEmpty()){
            throw new RuntimeException("Cups UOM Not Found");
        }

        // Get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();

        // Get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");

        if(americanCategoryOptional.isEmpty()){
            throw new RuntimeException("American Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");

        if(mexicanCategoryOptional.isEmpty()){
            throw new RuntimeException("Mexican Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        // Yummy Perfect Guacamole Recipe
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

                        Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd
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

                Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws
                """);
        guacamoleRecipe.setNotes(guacamoleNotes);

        guacamoleRecipe.addIngredient(new Ingredient("Ripe avocados", new BigDecimal(2), eachUom, guacamoleRecipe));
        guacamoleRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaSpoonUom, guacamoleRecipe));
        guacamoleRecipe.addIngredient(new Ingredient("Fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom, guacamoleRecipe));
        guacamoleRecipe.addIngredient(new Ingredient("Minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom, guacamoleRecipe));
        guacamoleRecipe.addIngredient(new Ingredient("Serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom, guacamoleRecipe));
        guacamoleRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom, guacamoleRecipe));
        guacamoleRecipe.addIngredient(new Ingredient("Freshly grated black pepper", new BigDecimal(2), dashUom, guacamoleRecipe));
        guacamoleRecipe.addIngredient(new Ingredient("Ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom, guacamoleRecipe));

        guacamoleRecipe.getCategories().add(americanCategory);
        guacamoleRecipe.getCategories().add(mexicanCategory);

        guacamoleRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamoleRecipe.setServings(4);
        guacamoleRecipe.setSource("Simple Recipes");

        // Add to return list
        recipes.add(guacamoleRecipe);

        // Yummy Spicy Grilled Chicken Tacos
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

                Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm
                """);

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("""
                We have a family motto and it is this: Everything goes better in a tortilla.
                Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled
                jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan
                on the stove comes wafting through the house.
                Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!
                First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet
                orange juice while the grill is heating. You can also use this time to prepare the taco toppings.
                Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble
                the tacos and dig in. The whole meal comes together in about 30 minutes!

                Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ
                """);
        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Finely grated orange zestr", new BigDecimal(1), tableSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Boneless chicken thighs", new BigDecimal(4), tableSpoonUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Small corn tortillasr", new BigDecimal(8), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Packed baby arugula", new BigDecimal(3), cupsUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Medium ripe avocados, slic", new BigDecimal(2), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Radishes, thinly sliced", new BigDecimal(4), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Cherry tomatoes, halved", new BigDecimal(".5"), pintUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Red onion, thinly sliced", new BigDecimal(".25"), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom, tacosRecipe));
        tacosRecipe.addIngredient(new Ingredient("Lime, cut into wedges", new BigDecimal(4), eachUom, tacosRecipe));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacosRecipe.setServings(4);
        tacosRecipe.setSource("Simple Recipes");

        recipes.add(tacosRecipe);
        
        return recipes;
    }

}
