package ku.cs.restaurant.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductDeleteDto {
    @NotBlank
    private UUID id;
}
