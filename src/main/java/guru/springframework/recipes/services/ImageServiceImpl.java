package guru.springframework.recipes.services;

import guru.springframework.recipes.domain.Recipe;
import guru.springframework.recipes.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private RecipeReactiveRepository recipeRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {

        Mono<Recipe> recipeMono = recipeRepository.findById(recipeId)
                .map(
                    recipe -> {
                        try {
                            Byte[] bytes = new Byte[file.getBytes().length];
                            int i = 0;
                            for(byte b : file.getBytes()){
                                bytes[i++] = b;
                            }
                            recipe.setImage(bytes);
                            return recipe;
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                            throw new RuntimeException(ioe);
                        }
                    }
                );

        recipeRepository.save(recipeMono.block()).block();

        return Mono.empty();
    }
}
