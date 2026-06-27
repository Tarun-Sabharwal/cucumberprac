package org.example.order.out.persistence;

import org.example.order.common.DomainService;
import org.example.order.core.domain.Order;
import org.example.order.core.port.ManageOrderPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@DomainService
public class OrderPersistenceAdapter implements ManageOrderPort {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
