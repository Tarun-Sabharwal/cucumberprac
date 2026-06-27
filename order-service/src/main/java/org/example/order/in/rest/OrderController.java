package org.example.order.in.rest;

import org.example.order.core.domain.Order;
import org.example.order.core.usecase.OrderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderUseCase orderUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody Order order) {
        return orderUseCase.createOrder(order);
    }

    @GetMapping
    public List<Order> getAll() {
        return orderUseCase.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderUseCase.getOrder(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id) {
        orderUseCase.cancelOrder(id);
    }
}
