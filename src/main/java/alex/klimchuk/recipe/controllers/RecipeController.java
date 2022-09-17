package alex.klimchuk.recipe.controllers;

import alex.klimchuk.recipe.dto.RecipeDto;
import alex.klimchuk.recipe.services.RecipeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Copyright Alex Klimchuk (c) 2022.
 */
@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "/recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeDto());
        return "/recipe/recipeForm";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findDtoById(Long.valueOf(id)));
        return "/recipe/recipeForm";
    }

    @PostMapping("/recipe")
    public String saveOrUpdate(@Valid @ModelAttribute RecipeDto recipeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(ex ->
                    log.debug("Something wrong here: ", ex.toString()));
            return "/recipe/recipeForm";
        }
        RecipeDto savedRecipeDto = recipeService.saveRecipeDto(recipeDto);
        return "redirect:/recipe/show/" + savedRecipeDto.getId();
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

}
