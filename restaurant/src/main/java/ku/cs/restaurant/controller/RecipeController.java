package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.recipe.UpdateQtyRequest;
import ku.cs.restaurant.entity.Recipe;
import ku.cs.restaurant.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    // Create a new recipe
    @PostMapping("/recipe")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        try {
            Recipe createdRecipe = recipeService.createRecipe(recipe);
            return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    @GetMapping("/recipe/{id}")
    public ResponseEntity<List<Recipe>> getRecipesByProductId(@PathVariable UUID id) {
        try {
            List<Recipe> recipes = recipeService.getRecipesByProductId(id);
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // Get recipe by ID using request body
    @PostMapping("/recipe/getById")
    public ResponseEntity<Recipe> getRecipeById(@RequestBody UUID id) {
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
    public ResponseEntity<Void> deleteRecipe(@RequestBody UUID id) {
        try {
            recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

