package guru.springframework.recipes.services;

import guru.springframework.recipes.commands.RecipeCommand;
import guru.springframework.recipes.converters.RecipeCommandToRecipe;
import guru.springframework.recipes.converters.RecipeToRecipeCommand;
import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        Mono<Recipe> recipe = recipeRepository.findById(id);
        return recipe;
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        return recipeRepository.save(recipeCommandToRecipe.convert(command))
                .map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        Mono<RecipeCommand> recipeCommandMono = recipeRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand command = recipeToRecipeCommand.convert(recipe);
                    command.getIngredients().forEach(rc -> {
                        rc.setRecipeId(command.getId());
                    });
                    return command;
                });
        return recipeCommandMono;
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id).block();
    }
}
