package ku.cs.restaurant.dto.recipe;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateQtyRequest {
    @NotBlank
    private UUID id;
    @NotBlank
    private int qty;
}
