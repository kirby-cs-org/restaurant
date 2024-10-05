package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.Payment.PaymentResponse;
import ku.cs.restaurant.dto.order.FoodOrder;
import ku.cs.restaurant.dto.order.OrderRequest;
import ku.cs.restaurant.dto.order.UpdateStatusRequest;
import ku.cs.restaurant.entity.*;
import ku.cs.restaurant.service.*;
import ku.cs.restaurant.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final UserService userService;
    private final ReceiptService receiptService;
    private final JwtUtils jwtUtils;
    private final OrderLineService orderLineService;

    public OrderController(OrderService orderService, PaymentService paymentService, UserService userService,
                           JwtUtils jwtUtils, ReceiptService receiptService, OrderLineService orderLineService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.receiptService = receiptService;
        this.orderLineService = orderLineService;
    }

    @PostMapping("/order")
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest orderRequest,
                                                       @RequestHeader("Authorization") String jwt) {
        try {
            String username = jwtUtils.getUserNameFromJwtToken(jwt);

            Optional<User> optionalUser = userService.getUserByUsername(username);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Receipt receipt = receiptService.createReceipt((orderRequest.calculateTotal()));
                Order createdOrder = orderService.createOrder(orderRequest.calculateTotal(), user, receipt);

                for (FoodOrder foodOrder : orderRequest.getFoods())
                    orderLineService.createOrderLine(foodOrder.getQty(), createdOrder, foodOrder.getFood());

                PaymentResponse response = paymentService.createPaymentLink(createdOrder);

                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // ดูทั้งหมด
    @GetMapping("/order")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderService.findOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ดูตามสถานะ
    @GetMapping("/order/status")
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestBody OrderStatus status) {
        try {
            List<Order> orders = orderService.findOrderByStatus(status);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // อัพเดทสถานะ
    @PatchMapping("/order")
    public ResponseEntity<Optional<Order>> updateOrderStatusById(@RequestBody UpdateStatusRequest request) {
        try {
            Optional<Order> updatedOrder = orderService.updateOrderStatusById(request.getId(), request.getStatus());
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
