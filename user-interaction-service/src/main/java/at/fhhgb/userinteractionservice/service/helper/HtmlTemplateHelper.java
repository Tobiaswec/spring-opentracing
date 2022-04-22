package at.fhhgb.userinteractionservice.service.helper;

import at.fhhgb.userinteractionservice.dto.RecipeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class HtmlTemplateHelper {

    private static class HtmlTemplates{
       static final String INDEX = "index.html";
    }

    @Autowired
    private SpringTemplateEngine templateEngine;

    public String generateRecipeListHtml(List<RecipeDto> recipes) {
        Context context = new Context();
        context.setVariable("recipes", recipes);

        return templateEngine.process(HtmlTemplates.INDEX, context);
    }
}
