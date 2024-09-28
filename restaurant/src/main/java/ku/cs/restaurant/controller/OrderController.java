package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.ingredient.UpdateStatusRequest;
import ku.cs.restaurant.entity.Order;
import ku.cs.restaurant.entity.OrderStatus;
import ku.cs.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class OrderController {
    @Autowired
    private OrderService service;

    // สร้าง
    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order createdOrder = service.createdOrder(order);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // ดูทั้งหมด
    @GetMapping("/order")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = service.findOrders();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // ดูตามสถานะ
    @GetMapping("/order/status")
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestBody OrderStatus status) {
        try {
            List<Order> orders = service.findByStatus(status);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // อัพเดทสถานะ
    @PatchMapping("/order")
    public ResponseEntity<Optional<Order>> updateOrderStatusById(@RequestBody UpdateStatusRequest request) {
        try {
            Optional<Order> updatedOrder = service.updateOrderStatusById(request.getId(), request.getStatus());
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
