package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.Ingredient;
import ku.cs.restaurant.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    List<Ingredient> findByStatus(Status status);
    Optional<Ingredient> findById(UUID id);
}
