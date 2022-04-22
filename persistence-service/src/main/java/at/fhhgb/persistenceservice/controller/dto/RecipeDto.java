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

    public RecipeDto() {}

    public RecipeDto(String title, List<String> ingredients, String description, String url, String imageUrl, String type) {
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public RecipeEntity toEntity() {
        return new RecipeEntity(title,ingredients,description,url,imageUrl,type);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
