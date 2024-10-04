package ku.cs.restaurant.controller;

import ku.cs.restaurant.entity.OrderLine;
import ku.cs.restaurant.service.OrderLineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OrderLineController {
    private final OrderLineService service;

    public OrderLineController(OrderLineService service) {
        this.service = service;
    }

    @PostMapping("/order_line")
    public ResponseEntity<OrderLine> createOrderLine(@RequestBody OrderLine orderLine) {
        try {
            OrderLine orderLines = service.createOrderLine(orderLine);
            return new ResponseEntity<>(orderLines, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/order_line")
    public ResponseEntity<List<OrderLine>> getAllOrderLine() {
        try {
            List<OrderLine> orderLines = service.findOrderLine();
            return new ResponseEntity<>(orderLines, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/order_line/quantity")
    public ResponseEntity<OrderLine> updateQty(@RequestBody OrderLine orderLine) {
        try {
            OrderLine updatedOrderLine = service.updateQuantity(orderLine);
            return new ResponseEntity<>(updatedOrderLine, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/order_line/total")
    public ResponseEntity<OrderLine> updateTotal(@RequestBody OrderLine orderLine) {
        try {
            OrderLine updatedOrderLine = service.updateTotal(orderLine);
            return new ResponseEntity<>(updatedOrderLine, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

