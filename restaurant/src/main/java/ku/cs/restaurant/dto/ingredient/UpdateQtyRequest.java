package ku.cs.restaurant.dto.ingredient;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateQtyRequest {
    private UUID id;
    private int qty;
}
