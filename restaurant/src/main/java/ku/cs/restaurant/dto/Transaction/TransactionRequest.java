package ku.cs.restaurant.dto.Transaction;

import jakarta.validation.constraints.NotBlank;
import ku.cs.restaurant.entity.Orders;
import ku.cs.restaurant.entity.Transaction;
import ku.cs.restaurant.entity.User;

import java.util.UUID;

public class TransactionRequest {
    @NotBlank
    private Transaction t_id;
    @NotBlank
    private User user_id;
    @NotBlank
    private Orders o_id;
}
