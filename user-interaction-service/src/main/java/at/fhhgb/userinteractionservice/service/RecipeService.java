package at.fhhgb.userinteractionservice.service;

import at.fhhgb.userinteractionservice.dto.RecipeDto;
import at.fhhgb.userinteractionservice.dto.RecipeType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RecipeService {


    public List<RecipeDto> getRecipes(RecipeType recipeType) {
        return Collections.emptyList();
    }

    public boolean delete(int recipeId) {
        return true;
    }

    public RecipeDto persistRecipe(RecipeDto recipe) {
        return new RecipeDto();
    }


}

