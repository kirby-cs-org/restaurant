package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.OrderLine;
import ku.cs.restaurant.entity.Orders;
import ku.cs.restaurant.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {
    private OrderLineRepository orderLineRepository;

    public OrderLine createdOrderline(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    public List<OrderLine> findOrderLine() {
        return orderLineRepository.findAll();
    }
}