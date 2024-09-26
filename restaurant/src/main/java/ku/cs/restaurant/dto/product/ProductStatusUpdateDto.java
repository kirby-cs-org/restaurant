package ku.cs.restaurant.dto.product;

import ku.cs.restaurant.entity.ProductStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductStatusUpdateDto {
    private UUID id;
    private ProductStatus status;
}
