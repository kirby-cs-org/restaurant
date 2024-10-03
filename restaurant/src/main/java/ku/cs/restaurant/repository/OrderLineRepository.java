package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.OrderLine;
import ku.cs.restaurant.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {
    Optional<OrderLine> findById(UUID id);
}
