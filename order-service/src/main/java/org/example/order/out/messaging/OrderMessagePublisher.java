package org.example.order.out.messaging;

import org.example.order.config.RabbitMQConfig;
import org.example.order.core.port.PublishOrderEventPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderMessagePublisher implements PublishOrderEventPort {

    private final RabbitTemplate rabbitTemplate;

    public OrderMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishOrderCreated(Long orderId, String product, int quantity) {
        OrderCreatedMessage message = new OrderCreatedMessage(orderId, product, quantity);
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ORDER_ROUTING_KEY, message);
    }
}
