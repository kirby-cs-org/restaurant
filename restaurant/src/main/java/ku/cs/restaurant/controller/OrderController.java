package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.Payment.PaymentResponse;
import ku.cs.restaurant.dto.food.FoodDto;
import ku.cs.restaurant.dto.food.FoodListDto;
import ku.cs.restaurant.dto.order.FoodOrder;
import ku.cs.restaurant.dto.order.OrderRequest;
import ku.cs.restaurant.dto.order.UpdateStatusRequest;
import ku.cs.restaurant.dto.order.UserResponse;
import ku.cs.restaurant.entity.*;
import ku.cs.restaurant.service.*;
import ku.cs.restaurant.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
                    orderLineService.createOrderLine(foodOrder.getQuantity(), createdOrder, foodOrder.getFood());

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

    @GetMapping("/order/{id}/user")
    public ResponseEntity<UserResponse> getUserByOrderId(@PathVariable("id") UUID id) {
        try {
            Order order = orderService.findOrderById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            User user = userService.getUserById(order.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setPhone(user.getPhone());
            userResponse.setRole(user.getRole());

            return new ResponseEntity<>(userResponse, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //แสดงใบเสร็จ
    @GetMapping("/order/{id}/receipt")
    public ResponseEntity<Receipt> getReceiptByOrderId(@PathVariable("id") UUID id) {
        try {
            Order order = orderService.findOrderById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));
            Receipt receipt = order.getReceipt();
            return new ResponseEntity<>(receipt, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order/{id}/food")
    public ResponseEntity<FoodListDto> getFoodByOrderId(@PathVariable("id") UUID id) {
        try {
            Order order = orderService.findOrderById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Order not found"));

            List<OrderLine> orderLines = order.getOrderLines();
            FoodListDto foodListDto = new FoodListDto();

            List<FoodDto> foodDtos = orderLines.stream()
                    .map(ol -> {
                        FoodDto foodDto = new FoodDto();
                        foodDto.setFood(ol.getFood());
                        foodDto.setQty(ol.getQty());
                        return foodDto;
                    })
                    .collect(Collectors.toList());

            foodListDto.setFoods(foodDtos);

            return ResponseEntity.ok(foodListDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
    public ResponseEntity<Order> updateOrderStatusById(@RequestBody UpdateStatusRequest request) {
        try {
            Optional<Order> optionalUpdatedOrder = orderService.updateOrderStatusById(request.getId(),
                    request.getStatus());
            if (optionalUpdatedOrder.isPresent()) {
                Order updatedOrder = optionalUpdatedOrder.get();
                return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
