package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.order.UpdateStatusRequest;
import ku.cs.restaurant.entity.Orders;
import ku.cs.restaurant.entity.OrderStatus;
import ku.cs.restaurant.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // สร้าง
    @PostMapping("/order")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders orders) {
        try {
            Orders createdOrders = service.createOrder(orders);
            return new ResponseEntity<>(createdOrders, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ดูทั้งหมด
    @GetMapping("/order")
    public ResponseEntity<List<Orders>> getAllOrders() {
        try {
            List<Orders> orders = service.findOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ดูตามสถานะ
    @GetMapping("/order/status")
    public ResponseEntity<List<Orders>> getOrdersByStatus(@RequestBody OrderStatus status) {
        try {
            List<Orders> orders = service.findOrderByStatus(status);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // อัพเดทสถานะ
    @PatchMapping("/order")
    public ResponseEntity<Optional<Orders>> updateOrderStatusById(@RequestBody UpdateStatusRequest request) {
        try {
            Optional<Orders> updatedOrder = service.updateOrderStatusById(request.getId(), request.getStatus());
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
