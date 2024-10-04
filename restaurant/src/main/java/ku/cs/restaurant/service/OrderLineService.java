package ku.cs.restaurant.service;

import ku.cs.restaurant.dto.orderLine.CreateRequest;
import ku.cs.restaurant.entity.Food;
import ku.cs.restaurant.entity.OrderLine;
import ku.cs.restaurant.entity.Orders;
import ku.cs.restaurant.exception.ResourceNotFoundException;
import ku.cs.restaurant.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderService orderService;
    private final FoodService foodService;

    public OrderLineService(OrderLineRepository orderLineRepository, OrderService orderService, FoodService foodService) {
        this.orderLineRepository = orderLineRepository;
        this.orderService = orderService;
        this.foodService = foodService;
    }

    public List<OrderLine> findOrderLine() {
        return orderLineRepository.findAll();
    }

    public OrderLine createOrderLine(CreateRequest orderLineRequest) {
        OrderLine newOrderLine = new OrderLine();
        newOrderLine.setId(orderLineRequest.getId());
        newOrderLine.setQty(orderLineRequest.getQty());

        Optional<Orders> optionalOrder = orderService.findOrderById(orderLineRequest.getId().getOrderId());
        if (optionalOrder.isPresent()) {
            newOrderLine.setOrder(optionalOrder.get()); // Set the Orders object
        } else {
            throw new ResourceNotFoundException("Order not found for ID: " + orderLineRequest.getId().getOrderId());
        }

        Optional<Food> optionalFood = foodService.getFoodById(orderLineRequest.getId().getFoodId());
        if (optionalFood.isPresent()) {
            newOrderLine.setFood(optionalFood.get()); // Set the Food object
        } else {
            throw new ResourceNotFoundException("Food not found for ID: " + orderLineRequest.getId().getFoodId());
        }

        return orderLineRepository.save(newOrderLine);
    }

    public OrderLine updateQuantity(OrderLine orderLine) {
        orderLine.setQty(orderLine.getQty());
        return orderLineRepository.save(orderLine);
    }
}