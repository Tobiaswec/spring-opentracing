package at.fhhgb.persistenceservice.service;

import at.fhhgb.persistenceservice.entity.RecipeEntity;
import at.fhhgb.persistenceservice.entity.RecipeType;
import at.fhhgb.persistenceservice.repository.RecipeRepository;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<RecipeEntity> getRecipes(RecipeType recipeType) {
        if (recipeType.ordinal() != RecipeType.ALL.ordinal()) {
            return recipeRepository.findAllByType(recipeType.name().toLowerCase());
        } else {
            return recipeRepository.findAll();
        }
    }

    public Optional<RecipeEntity>getRecipe(int id){

        return recipeRepository.findById(id);
    }

    public boolean delete(int recipeId) {
        if(recipeRepository.existsById(recipeId)){
            recipeRepository.deleteById(recipeId);
            return true;
        }
        return false;
    }

    public RecipeEntity persistRecipe(RecipeEntity recipe) {
        return recipeRepository.save(recipe);
    }


}

