package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.recipe.CreateRequest;
import ku.cs.restaurant.dto.recipe.UpdateQtyRequest;
import ku.cs.restaurant.entity.Recipe;
import ku.cs.restaurant.entity.RecipeKey;
import ku.cs.restaurant.service.RecipeService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // Create a new recipe
    @PostMapping("/recipe")
    public ResponseEntity<Recipe> createRecipe(@RequestBody CreateRequest recipe) {
        try {
            Recipe createdRecipe = recipeService.createRecipe(recipe);
            return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all recipes
    @GetMapping("/recipe")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        try {
            List<Recipe> recipes = recipeService.getAllRecipes();
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get recipe by ID using request body
    @PostMapping("/recipe/getById")
    public ResponseEntity<Recipe> getRecipeById(@RequestBody RecipeKey id) {
        try {
            Optional<Recipe> recipe = recipeService.getRecipeById(id);
            return recipe.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a recipe using request body
    @PatchMapping("/recipe")
    public ResponseEntity<Optional<Recipe>> updateRecipe(@RequestBody UpdateQtyRequest updateQtyRequest) {
        try {
            Optional<Recipe> updatedRecipe = recipeService.updateRecipe(updateQtyRequest.getId(), updateQtyRequest.getQty());
            return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a recipe using request body
    @DeleteMapping("/recipe/delete")
    public ResponseEntity<Void> deleteRecipe(@RequestBody RecipeKey id) {
        try {
            recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

