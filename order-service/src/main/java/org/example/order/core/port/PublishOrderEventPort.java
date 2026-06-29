package org.example.order.core.port;

public interface PublishOrderEventPort {
    void publishOrderCreated(Long orderId, String product, int quantity);
}
