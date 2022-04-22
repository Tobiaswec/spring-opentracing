package at.fhhgb.userinteractionservice.service;

import at.fhhgb.userinteractionservice.client.RecipeRestTemplate;
import at.fhhgb.userinteractionservice.dto.RecipeCreationDto;
import at.fhhgb.userinteractionservice.dto.RecipeDto;
import at.fhhgb.userinteractionservice.dto.RecipeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRestTemplate template;

    public ResponseEntity<List<RecipeCreationDto>> getRecipes(RecipeType recipeType) {
        return new ResponseEntity<List<RecipeCreationDto>>(template.getRecipes(recipeType),HttpStatus.OK);
    }

    public ResponseEntity<String> delete(int recipeId) {
        return template.deleteRecipe(recipeId) ? ResponseEntity.ok().build() : new ResponseEntity<String>("Could not delete Recipe", HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    public ResponseEntity persistRecipe(RecipeCreationDto recipe) {
        Optional<RecipeDto> entity = template.postRecipe(recipe);
        return entity.map(recipeDto -> new ResponseEntity(recipeDto, HttpStatus.CREATED)).orElseGet(()
                -> new ResponseEntity("Could not save Recipe", HttpStatus.INTERNAL_SERVER_ERROR));
    }


}

