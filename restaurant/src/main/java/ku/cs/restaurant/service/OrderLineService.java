package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.OrderLine;
import ku.cs.restaurant.entity.Orders;
import ku.cs.restaurant.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineService {
    private OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public List<OrderLine> findOrderLine() {
        return orderLineRepository.findAll();
    }

    public OrderLine createOrderLine(OrderLine orderLine) {
        return orderLineRepository.save(orderLine);
    }

    public OrderLine updateQuantity(OrderLine orderLine) {
        orderLine.setO_qty(orderLine.getO_qty());
        return orderLineRepository.save(orderLine);
    }

    public OrderLine updateTotal(OrderLine orderLine) {
        orderLine.setTotal_each(orderLine.getTotal_each());
        return orderLineRepository.save(orderLine);
    }
}