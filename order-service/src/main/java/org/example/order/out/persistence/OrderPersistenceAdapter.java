package org.example.order.out.persistence;

import org.example.order.core.domain.Order;
import org.example.order.core.port.ManageOrderPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceAdapter implements ManageOrderPort {

    @Autowired
    private OrderJpaRepository repository;

    @Override
    public Order save(Order order) {
        return toDomain(repository.save(toEntity(order)));
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setProduct(order.getProduct());
        entity.setQuantity(order.getQuantity());
        entity.setStatus(order.getStatus());
        return entity;
    }

    private Order toDomain(OrderEntity entity) {
        return new Order(entity.getId(), entity.getProduct(), entity.getQuantity(), entity.getStatus());
    }
}
