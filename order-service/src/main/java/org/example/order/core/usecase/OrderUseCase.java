package org.example.order.core.usecase;

import org.example.order.common.UseCase;
import org.example.order.config.RabbitMQConfig;
import org.example.order.core.domain.Order;
import org.example.order.core.domain.OrderStatus;
import org.example.order.core.port.ManageOrderPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@UseCase
public class OrderUseCase {

    @Autowired
    private ManageOrderPort manageOrderPort;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Order createOrder(Order order) {
        if (order.getQuantity() < 1 || order.getQuantity() > 90) {
            throw new IllegalArgumentException("Quantity must be between 1 and 90");
        }
        order.setStatus(OrderStatus.PENDING);
        Order saved = manageOrderPort.save(order);

        // publish event so inventory-service deducts stock
        OrderCreatedMessage msg = new OrderCreatedMessage(saved.getId(), saved.getProduct(), saved.getQuantity());
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ORDER_ROUTING_KEY, msg);

        return saved;
    }

    public Optional<Order> getOrder(Long id) {
        return manageOrderPort.findById(id);
    }

    public List<Order> getAllOrders() {
        return manageOrderPort.findAll();
    }

    public void cancelOrder(Long id) {
        Order order = manageOrderPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        order.setStatus(OrderStatus.CANCELLED);
        manageOrderPort.save(order);
    }
}
