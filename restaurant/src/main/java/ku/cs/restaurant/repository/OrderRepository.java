package ku.cs.restaurant.repository;

import ku.cs.restaurant.entity.Orders;
import ku.cs.restaurant.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {
    List<Orders> findByStatus(OrderStatus status);
    Optional<Orders> findById(UUID id);
}
