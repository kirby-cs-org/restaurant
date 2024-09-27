package ku.cs.restaurant.dto.ingredient;

import ku.cs.restaurant.entity.Status;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateStatusRequest {
    private UUID id;
    private Status status;
}
