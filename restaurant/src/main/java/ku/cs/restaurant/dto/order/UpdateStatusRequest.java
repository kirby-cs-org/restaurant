package ku.cs.restaurant.dto.order;

import ku.cs.restaurant.entity.OrderStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateStatusRequest {
    private UUID id;
    private OrderStatus status;
}
