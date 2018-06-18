package guru.springframework.recipes.controllers;

import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting index page");

/*
        Flux<Recipe> recipes = recipeService.getRecipes();
        Mono<List<Recipe>> monoRecipes = recipes.collectList();
        List<Recipe> listRecipes = monoRecipes.block();

        model.addAttribute("recipes", listRecipes);
*/

        model.addAttribute("recipes", recipeService.getRecipes().collectList());

        return "index";
    }
}
