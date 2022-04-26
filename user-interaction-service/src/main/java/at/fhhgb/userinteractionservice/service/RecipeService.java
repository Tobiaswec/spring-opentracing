package at.fhhgb.userinteractionservice.service;

import at.fhhgb.userinteractionservice.client.RecipeRestTemplate;
import at.fhhgb.userinteractionservice.dto.RecipeCreationDto;
import at.fhhgb.userinteractionservice.dto.RecipeDto;
import at.fhhgb.userinteractionservice.dto.RecipeType;
import at.fhhgb.userinteractionservice.service.helper.HtmlTemplateHelper;
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

    @Autowired
    private HtmlTemplateHelper htmlTemplateHelper;

    public String getRecipesHtml(RecipeType recipeType){
        return htmlTemplateHelper.generateRecipeListHtml(template.getRecipes(recipeType),recipeType);
    }

    public String getRecipeHtml(int id){
        return htmlTemplateHelper.generateRecipeHtml(template.getRecipe(id));
    }


    public ResponseEntity<List<RecipeDto>> getRecipes(RecipeType recipeType) {
        return new ResponseEntity<List<RecipeDto>>(template.getRecipes(recipeType),HttpStatus.OK);
    }

    public ResponseEntity<String> delete(int recipeId) {
        return template.deleteRecipe(recipeId) ? ResponseEntity.ok().build() : new ResponseEntity<String>("Could not delete Recipe", HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    public ResponseEntity persistRecipe(RecipeCreationDto recipe) {
        Optional<RecipeDto> entity = template.postRecipe(recipe);
        return entity.map(recipeDto -> new ResponseEntity(recipeDto, HttpStatus.CREATED)).orElseGet(()
                -> new ResponseEntity("Could not save Recipe", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public ResponseEntity<Optional<RecipeDto>> getRecipe(int id){
        Optional<RecipeDto> entity=Optional.of(template.getRecipe(id));
        return entity.map(recipeDto -> new ResponseEntity(recipeDto, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity("Could not get Recipe with id="+id, HttpStatus.INTERNAL_SERVER_ERROR));
    }


}

