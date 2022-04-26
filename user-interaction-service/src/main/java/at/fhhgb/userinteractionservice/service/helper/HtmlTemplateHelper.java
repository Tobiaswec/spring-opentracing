package at.fhhgb.userinteractionservice.service.helper;

import at.fhhgb.userinteractionservice.dto.RecipeCreationDto;
import at.fhhgb.userinteractionservice.dto.RecipeDto;
import at.fhhgb.userinteractionservice.dto.RecipeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class HtmlTemplateHelper {

    private static class HtmlTemplates{
       static final String INDEX = "index.html";
       static final String DETAILS = "details.html";
    }

    @Autowired
    private SpringTemplateEngine templateEngine;

    public String generateRecipeListHtml(List<RecipeDto> recipes, RecipeType recipeType) {
        Context context = new Context();
        context.setVariable("type", recipeType.name());
        context.setVariable("recipes", recipes);
        context.setVariable("recipeForm",new RecipeCreationDto());

        return templateEngine.process(HtmlTemplates.INDEX, context);
    }

    public String generateRecipeHtml(RecipeDto recipe) {
        Context context = new Context();
        context.setVariable("recipe", recipe);

        return templateEngine.process(HtmlTemplates.DETAILS, context);
    }
}
