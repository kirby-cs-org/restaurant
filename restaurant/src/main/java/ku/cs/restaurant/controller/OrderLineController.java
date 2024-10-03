package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.order.UpdateStatusRequest;
import ku.cs.restaurant.entity.OrderLine;
import ku.cs.restaurant.entity.Orders;
import ku.cs.restaurant.service.OrderLineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderLineController {
    private OrderLineService service;

    public OrderLineController(OrderLineService service) {
        this.service = service;
    }

    @PostMapping("/orderline")
    public ResponseEntity<OrderLine> createOrderLine(@RequestBody OrderLine orderLine) {
        try {
            OrderLine createdOrderline = service.createdOrderline(orderLine);
            return new ResponseEntity<>(createdOrderline, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/orderline")
    public ResponseEntity<List<OrderLine>> getAllOrders() {
        try {
            List<OrderLine> orderLine = service.findOrderLine();
            return new ResponseEntity<>(orderLine, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
