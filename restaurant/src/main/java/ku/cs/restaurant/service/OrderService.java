package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Orders;
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
    public Orders createOrder(Orders orders) {
        return orderRepository.save(orders);
    }
    // ดูทั้งหมด
    public List<Orders> findOrders() {
        return orderRepository.findAll();
    }
    // ดูตามสถานะ
    public List<Orders> findOrderByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
    // อัพเดทสถานะ 
    public Optional<Orders> updateOrderStatusById(UUID id, OrderStatus status) {
        Optional<Orders> optionalOrder = orderRepository.findById(id);
        optionalOrder.ifPresent(orders -> {
            orders.setStatus(status);
            orderRepository.save(orders);
        });

        return optionalOrder;
    }
}
