package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Recipe;
import ku.cs.restaurant.entity.RecipeKey;
import ku.cs.restaurant.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(RecipeKey id) {
        return recipeRepository.findById(id);
    }

    public Optional<Recipe> updateRecipe(RecipeKey id, int qty) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            recipe.setQty(qty);
            recipeRepository.save(recipe);
            return Optional.of(recipe);
        }
        return Optional.empty();
    }


    public void deleteRecipe(RecipeKey id) {
        recipeRepository.deleteById(id);
    }
}
