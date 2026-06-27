package org.example.order.core.usecase;

import org.example.order.common.UseCase;
import org.example.order.core.domain.Order;
import org.example.order.core.domain.OrderStatus;
import org.example.order.core.port.ManageOrderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@UseCase
public class OrderUseCase {

    @Autowired
    private ManageOrderPort manageOrderPort;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    public Order createOrder(Order order) {
        if (order.getQuantity() < 1 || order.getQuantity() > 90) {
            throw new IllegalArgumentException("Quantity must be between 1 and 90");
        }
        // deduct stock from inventory — inventory will throw error if not enough stock
        restTemplate.put(
                inventoryServiceUrl + "/api/inventory/deduct?name={name}&quantity={quantity}",
                null,
                order.getProduct(),
                order.getQuantity()
        );
        order.setStatus(OrderStatus.PENDING);
        return manageOrderPort.save(order);
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
