package at.fhhgb.persistenceservice.config;


import at.fhhgb.persistenceservice.entity.RecipeEntity;
import at.fhhgb.persistenceservice.entity.RecipeType;
import at.fhhgb.persistenceservice.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class InitialDataLoader {

    @Autowired
    private RecipeRepository recipeRepository;



    @PostConstruct
    public void loadInitialData(){

        RecipeEntity r1= new RecipeEntity("Eierreis", Arrays.asList("Eier", "Reis"),"mix ois","www.google.at", "https://images.ichkoche.at/data/image/variations/620x434/9/gebratener-eierreis-mit-gemuese-und-cashewkernen-img-86415.jpg", RecipeType.VEGETARIAN.name());

        recipeRepository.save(r1);
    }
}
