package at.fhhgb.userinteractionservice.controller;

import at.fhhgb.userinteractionservice.dto.RecipeCreationDto;
import at.fhhgb.userinteractionservice.dto.RecipeType;
import at.fhhgb.userinteractionservice.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, path = "/html")
    public String getRecipesHTML(@RequestParam String type){
        RecipeType recipeType;
        try {
            recipeType = RecipeType.valueOf(type);
        }catch (IllegalArgumentException e){
            recipeType = RecipeType.ALL;
        }
        return recipeService.getRecipesHtml(recipeType);
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
    public ResponseEntity persistRecipe(@RequestBody RecipeCreationDto recipe){
        return recipeService.persistRecipe(recipe);
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int recipeId){
        return recipeService.delete(recipeId);
    }
}
