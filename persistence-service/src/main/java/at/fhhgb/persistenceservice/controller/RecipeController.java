package at.fhhgb.persistenceservice.controller;

import at.fhhgb.persistenceservice.entity.RecipeEntity;
import at.fhhgb.persistenceservice.controller.dto.RecipeDto;
import at.fhhgb.persistenceservice.entity.RecipeType;
import at.fhhgb.persistenceservice.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecipeEntity>> getRecipes(@RequestParam String type){
        RecipeType recipeType = RecipeType.ALL;
        try {
            recipeType = RecipeType.valueOf(type);
            }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return new ResponseEntity<>(recipeService.getRecipes(recipeType), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RecipeEntity persistRecipe(@RequestBody RecipeDto recipe){
        return recipeService.persistRecipe(recipe.toEntity());
    }

    @DeleteMapping(path = "{recipeId}")
    public Boolean deleteRecipe(@PathVariable int recipeId){
        return recipeService.delete(recipeId);
    }
}
