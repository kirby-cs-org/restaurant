package ku.cs.restaurant.dto.Receipt;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateRequest {
    @NotBlank
    private UUID id;

    @NotBlank
    private double total;

    @NotBlank
    private double amonut;
}
