package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.ingredient.UpdateQtyRequest;
import ku.cs.restaurant.dto.ingredient.UpdateStatusRequest;
import ku.cs.restaurant.entity.Ingredient;
import ku.cs.restaurant.entity.Status;
import ku.cs.restaurant.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class IngredientController {
    @Autowired
    private IngredientService service;

    // Create a new ingredient
    @PostMapping("/ingredient")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        try {
            Ingredient createdIngredient = service.createIngredient(ingredient);
            return new ResponseEntity<>(createdIngredient, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get ingredients by status
    @GetMapping("/ingredient/status")
    public ResponseEntity<List<Ingredient>> findIngredientsByStatus(@RequestBody Status status) {
        try {
            List<Ingredient> ingredients = service.findIngredientsByStatus(status);
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Get an ingredient by ID
    @GetMapping("/ingredient/{id}")
    public ResponseEntity<Ingredient> findIngredientById(@PathVariable UUID id) {
        return service.findIngredientById(id)
                .map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all ingredients
    @GetMapping("/ingredient")
    public ResponseEntity<List<Ingredient>> findIngredients() {
        List<Ingredient> ingredients = service.findIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    // Update quantity of an ingredient
    @PatchMapping("/ingredient")
    public ResponseEntity<Ingredient> increaseQty(@RequestBody UpdateQtyRequest request) {
        try {
            Optional<Ingredient> updatedIngredient = service.updateQty(request.getId(), request.getQty());
            return updatedIngredient
                    .map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update ingredient status
    @PatchMapping("/ingredient/status")
    public ResponseEntity<Ingredient> updateStatus(@RequestBody UpdateStatusRequest request) {
        try {
            Optional<Ingredient> updatedIngredient = service.updateStatus(request.getId(), request.getStatus());
            return updatedIngredient
                    .map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Delete an ingredient
    @DeleteMapping("/ingredient")
    public ResponseEntity<Void> deleteIngredient(@RequestBody UUID id) {
        try {
            service.deleteIngredient(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

