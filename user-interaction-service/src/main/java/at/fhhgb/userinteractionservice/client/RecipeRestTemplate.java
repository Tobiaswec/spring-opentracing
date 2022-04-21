package at.fhhgb.userinteractionservice.client;

import at.fhhgb.userinteractionservice.dto.RecipeDto;
import at.fhhgb.userinteractionservice.dto.RecipeEntity;
import at.fhhgb.userinteractionservice.dto.RecipeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RecipeRestTemplate {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private RestTemplate rest = new RestTemplate();

    public List<RecipeDto> getRecipes(RecipeType recipeType)
    {
        try {
            return rest.exchange("http://localhost:8888/api/recipe", HttpMethod.GET, null, new ParameterizedTypeReference<List<RecipeDto>>() {
            }, Map.of("type",recipeType.name())).getBody();
        }catch (RestClientException ex){
            logger.error("getRecipes()",ex);
            return Collections.emptyList();
        }
    }


    public Boolean deleteRecipe(int recipeId) {
        try {
            return rest.exchange("http://localhost:8888/api/recipe/" + recipeId, HttpMethod.DELETE, null, Boolean.class).getBody();
        } catch (RestClientException ex) {
            logger.error("deleteRecipe()", ex);
            return false;
        }
    }

    public Optional<RecipeEntity> postRecipe(RecipeDto recipe) {
        try {
            HttpEntity<RecipeDto> requestEntity = new HttpEntity<>(recipe);
            return Optional.ofNullable(rest.exchange("http://localhost:8888/api/recipe/", HttpMethod.POST, requestEntity, RecipeEntity.class).getBody());
        } catch (RestClientException ex) {
            logger.error("postRecipe()", ex);
            return Optional.empty();
        }
    }
}