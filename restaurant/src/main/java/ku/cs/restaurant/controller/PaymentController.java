package ku.cs.restaurant.controller;

import com.sun.net.httpserver.Authenticator;
import ku.cs.restaurant.entity.Order;
import ku.cs.restaurant.entity.OrderStatus;
import ku.cs.restaurant.service.OrderService;
import ku.cs.restaurant.service.PaymentService;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;


    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @PostMapping("/payment/success/{id}")
    public ResponseEntity<Order> paymentSuccess(@PathVariable UUID id) {
        Optional<Order> updatedOrder = orderService.updateOrderStatusById(id, OrderStatus.COMPLETE);
        return updatedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

