package at.fhhgb.userinteractionservice.dto;
import java.util.ArrayList;
import java.util.List;


public class RecipeCreationDto {
    String title;
    List<String> ingredients = new ArrayList<>();
    String description;
    String url;
    String imageUrl;
    String type;

}
