package at.fhhgb.persistenceservice.entity;

import at.fhhgb.persistenceservice.util.StringCommaConverter;

import javax.persistence.*;
import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity(name="recipies")
public class RecipeEntity {
    @Id
    @GeneratedValue
    int id;

    @Column
    String title;

    @Convert(converter = StringCommaConverter.class)
    @Column
    List<String> ingredients = new ArrayList<>();

    @Column
    String description;

    @Column
    String url;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name="type")
    String type;

    @Column(name="date")
    OffsetDateTime date=OffsetDateTime.now();

    public RecipeEntity() {
    }

    public RecipeEntity(String title, List<String> ingredients, String description, String url, String imageUrl, String type) {
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
