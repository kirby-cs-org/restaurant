package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Ingredient;
import ku.cs.restaurant.repository.IngredientRepository;
import ku.cs.restaurant.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository repository;

    // Create a new ingredient
    public Ingredient createIngredient(Ingredient ingredient) {
        return repository.save(ingredient);
    }

    // Find ingredients by status (e.g., OUT_OF_STOCK, IN_STOCK)
    public List<Ingredient> findIngredientsByStatus(Status status) {
        return repository.findByStatus(status);
    }

    // Find an ingredient by ID
    public Optional<Ingredient> findIngredientById(UUID id) {
        return repository.findById(id);
    }

    // Find all ingredients
    public List<Ingredient> findIngredients() {
        return repository.findAll();
    }

    // update quantity of an ingredient
    public Optional<Ingredient> updateQty(UUID id, int amount) {
        Optional<Ingredient> optionalIngredient = repository.findById(id);
        optionalIngredient.ifPresent(ingredient -> {
            ingredient.setQty(ingredient.getQty() + amount);

            // Set status to OUT_OF_STOCK if quantity is 0 or less
            if (ingredient.getQty() <= 0) {
                ingredient.setStatus(Status.OUT_OF_STOCK);
                ingredient.setQty(0); // Ensure quantity doesn't go negative
            }

            repository.save(ingredient);
        });
        return optionalIngredient; // Return the updated ingredient or empty if not found
    }


    // Update ingredient status
    public Optional<Ingredient> updateStatus(UUID id, Status newStatus) {
        Optional<Ingredient> optionalIngredient = repository.findById(id);
        optionalIngredient.ifPresent(ingredient -> {
            ingredient.setStatus(newStatus);
            repository.save(ingredient);
        });
        return optionalIngredient; // Return the updated ingredient or empty if not found
    }

    // Delete an ingredient
    public void deleteIngredient(UUID id) {
        repository.deleteById(id);
    }
}
