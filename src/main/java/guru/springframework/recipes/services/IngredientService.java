package guru.springframework.recipes.services;

import guru.springframework.recipes.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);
    Mono<Void> deleteById(String recipeId, String id);
}
