package org.example.order.core.usecase;

import org.example.order.common.UseCase;
import org.example.order.core.domain.Order;
import org.example.order.core.domain.OrderStatus;
import org.example.order.core.port.CheckInventoryPort;
import org.example.order.core.port.ManageOrderPort;
import org.example.order.core.port.PublishOrderEventPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@UseCase
public class OrderUseCase {

    @Autowired
    private ManageOrderPort manageOrderPort;

    @Autowired
    private PublishOrderEventPort publishOrderEventPort;

    @Autowired
    private CheckInventoryPort checkInventoryPort;

    public Order createOrder(Order order) {
        if (order.getQuantity() < 1 || order.getQuantity() > 90) {
            throw new IllegalArgumentException("Quantity must be between 1 and 90");
        }

        int availableStock = checkInventoryPort.getAvailableStock(order.getProduct());
        if (order.getQuantity() > availableStock) {
            throw new IllegalArgumentException("Insufficient stock for '" + order.getProduct()
                    + "': requested " + order.getQuantity() + ", available " + availableStock);
        }

        order.setStatus(OrderStatus.PENDING);
        Order saved = manageOrderPort.save(order);

        // publish event so inventory-service deducts stock
        publishOrderEventPort.publishOrderCreated(saved.getId(), saved.getProduct(), saved.getQuantity());

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
