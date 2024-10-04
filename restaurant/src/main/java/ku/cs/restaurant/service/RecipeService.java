package ku.cs.restaurant.service;

import ku.cs.restaurant.dto.recipe.CreateRequest;
import ku.cs.restaurant.entity.Food;
import ku.cs.restaurant.entity.Ingredient;
import ku.cs.restaurant.entity.Recipe;
import ku.cs.restaurant.entity.RecipeKey;
import ku.cs.restaurant.exception.ResourceNotFoundException;
import ku.cs.restaurant.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final FoodService foodService;
    private final IngredientService ingredientService;

    public RecipeService(RecipeRepository recipeRepository, FoodService foodService, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.foodService = foodService;
        this.ingredientService = ingredientService;
    }

    public Recipe createRecipe(CreateRequest recipe) {
        Recipe newRecipe = new Recipe();
        newRecipe.setId(recipe.getId());
        newRecipe.setQty(recipe.getQty());

        Optional<Food> optionalFood = foodService.getFoodById(recipe.getId().getFoodId());
        if (optionalFood.isPresent()) {
            newRecipe.setFood(optionalFood.get()); // Set the Food object
        } else {
            throw new ResourceNotFoundException("Food not found for ID: " + recipe.getId().getFoodId());
        }

        Optional<Ingredient> optionalIngredient = ingredientService.findIngredientById(recipe.getId().getIngredientId());
        if (optionalIngredient.isPresent()) {
            newRecipe.setIngredient(optionalIngredient.get()); // Set the Ingredient object
        } else {
            throw new ResourceNotFoundException("Ingredient not found for ID: " + recipe.getId().getIngredientId());
        }

        return recipeRepository.save(newRecipe);
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
