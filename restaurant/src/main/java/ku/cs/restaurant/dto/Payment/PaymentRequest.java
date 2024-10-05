package ku.cs.restaurant.dto.Payment;

import jakarta.validation.constraints.NotBlank;
import ku.cs.restaurant.entity.Order;
import ku.cs.restaurant.entity.Transaction;
import ku.cs.restaurant.entity.User;

public class PaymentRequest {
    @NotBlank
    private Transaction t_id;
    @NotBlank
    private User user_id;
    @NotBlank
    private Order o_id;
}
