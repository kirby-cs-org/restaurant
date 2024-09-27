package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Recipe;
import ku.cs.restaurant.entity.Status;
import ku.cs.restaurant.repository.ProductRepository;
import ku.cs.restaurant.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ProductRepository productRepository;

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(UUID id) {
        return recipeRepository.findById(id);
    }

    // Get recipes by product ID
    public List<Recipe> getRecipesByProductId(UUID productId) {
        return recipeRepository.findRecipeByProductId(productId); // Assuming you have this method in the repository
    }

    public Optional<Recipe> updateRecipe(UUID id, int amount) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            recipe.setQty(recipe.getQty() + amount);

            // Check if the product's quantity is less than the ingredient's quantity
            if (recipe.getProduct() != null && recipe.getProduct().getQty() < recipe.getIngredient().getQty()) {
                recipe.getProduct().setStatus(Status.OUT_OF_STOCK);
                // You might want to save the product's new status as well
                productRepository.save(recipe.getProduct()); // Assuming you have a product repository
            }

            recipeRepository.save(recipe);
            return Optional.of(recipe);
        }

        return Optional.empty();
    }



    public void deleteRecipe(UUID id) {
        recipeRepository.deleteById(id);
    }
}
