package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.Product;
import ku.cs.restaurant.entity.Recipe;
import ku.cs.restaurant.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByStatus(Status status);
    Optional<Product> findById(UUID id);
}
