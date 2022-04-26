package at.fhhgb.userinteractionservice.controller;

import at.fhhgb.userinteractionservice.client.RecipeRestTemplate;
import at.fhhgb.userinteractionservice.dto.RecipeCreationDto;
import at.fhhgb.userinteractionservice.dto.RecipeDto;
import at.fhhgb.userinteractionservice.dto.RecipeType;
import at.fhhgb.userinteractionservice.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private static class HtmlTemplates{
        static final String INDEX = "index.html";
        static final String DETAILS = "details.html";
    }

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRestTemplate restTemplate;

    @GetMapping("/html")
    public String getAllRecipesHTML(@PathVariable(value="type", required = false) String type, Model model) {
        return getRecipesHTML(RecipeType.ALL.name(),model);

    }

    @GetMapping("/html/{type}")
    public String getRecipesHTML(@PathVariable(value="type", required = false) String type, Model model){

        RecipeType recipeType;
        try {
            recipeType = RecipeType.valueOf(type);
        }catch (IllegalArgumentException e){
            recipeType = RecipeType.ALL;
        }
        model.addAttribute("recipeForm", new RecipeCreationDto());
        model.addAttribute("recipes",restTemplate.getRecipes(recipeType));
        return HtmlTemplates.INDEX;

    }

    @GetMapping("/html/details/{id}")
    public String getRecipeDetails(@PathVariable(value="id") int id, Model model){
        model.addAttribute("recipe",restTemplate.getRecipe(id));

        return HtmlTemplates.DETAILS;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getRecipes(@RequestParam String type){
        RecipeType recipeType = RecipeType.ALL;
        try {
            recipeType = RecipeType.valueOf(type);
            }catch (IllegalArgumentException e){
            return new ResponseEntity<String>("Type invalid",HttpStatus.BAD_REQUEST);
        }
        return recipeService.getRecipes(recipeType);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String persistRecipe(@ModelAttribute("recipeForm") RecipeCreationDto recipe, Model model){
        recipeService.persistRecipe(recipe);
        return getRecipesHTML(RecipeType.ALL.name(), model);
    }

    @DeleteMapping("/html/{id}")
    public String deleteRecipe(@PathVariable(value="id") int id, Model model){
        recipeService.delete(id);
        return getRecipesHTML(RecipeType.ALL.name(), model);
    }
}
