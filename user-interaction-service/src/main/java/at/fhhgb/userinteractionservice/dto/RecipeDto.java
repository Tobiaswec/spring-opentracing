package at.fhhgb.userinteractionservice.dto;
import java.util.ArrayList;
import java.util.List;


public class RecipeDto {
    int id;
    String title;
    List<String> ingredients = new ArrayList<>();
    String description;
    String url;
    //(name = "image_url")
    String imageUrl;
    String type;

}
