package org.example.order.core.port;

import org.example.order.core.domain.Order;
import java.util.List;
import java.util.Optional;

public interface ManageOrderPort {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    void deleteById(Long id);
}
