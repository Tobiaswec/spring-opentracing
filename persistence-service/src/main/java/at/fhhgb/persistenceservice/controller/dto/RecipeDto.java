package at.fhhgb.persistenceservice.controller.dto;

import at.fhhgb.persistenceservice.entity.RecipeEntity;

import java.util.ArrayList;
import java.util.List;

public class RecipeDto {
    String title;
    List<String> ingredients = new ArrayList<>();
    String description;
    String url;
    String imageUrl;
    String type;

    public RecipeEntity toEntity() {
        return new RecipeEntity(title,ingredients,description,url,imageUrl,type);
    }
}
