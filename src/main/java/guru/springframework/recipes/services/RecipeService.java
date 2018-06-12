package guru.springframework.recipes.services;

import guru.springframework.recipes.commands.RecipeCommand;
import guru.springframework.recipes.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);

    Mono<RecipeCommand> findCommandById(String id);

    void deleteById(String id);
}
