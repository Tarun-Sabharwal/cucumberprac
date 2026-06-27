package org.example.inventory.in.messaging;

import org.example.inventory.core.usecase.InventoryUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageConsumer {

    private final InventoryUseCase inventoryUseCase;

    public OrderMessageConsumer(InventoryUseCase inventoryUseCase) {
        this.inventoryUseCase = inventoryUseCase;
    }

    @RabbitListener(queues = "order.created.queue")
    public void handleOrderCreated(OrderCreatedMessage message) {
        inventoryUseCase.deductStock(message.getProduct(), message.getQuantity());
    }
}
