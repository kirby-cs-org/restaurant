package ku.cs.restaurant.dto.recipe;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateQtyRequest {
    private UUID id;
    private int qty;
}
