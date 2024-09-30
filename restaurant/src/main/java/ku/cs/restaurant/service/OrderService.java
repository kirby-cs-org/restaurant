package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Order;
import ku.cs.restaurant.repository.OrderRepository;
import ku.cs.restaurant.entity.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    // สร้างออเดอร์ใหม่ 
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
    // ดูทั้งหมด
    public List<Order> findOrders() {
        return orderRepository.findAll();
    }
    // ดูตามสถานะ
    public List<Order> findOrderByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
    // อัพเดทสถานะ 
    public Optional<Order> updateOrderStatusById(UUID id, OrderStatus status) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        optionalOrder.ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });

        return optionalOrder;
    }
}
