package org.example.order.core.port;

public interface CheckInventoryPort {
    int getAvailableStock(String product);
}
