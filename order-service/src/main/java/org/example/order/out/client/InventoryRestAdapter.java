package org.example.order.out.client;

import org.example.order.core.port.CheckInventoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryRestAdapter implements CheckInventoryPort {

    private final RestTemplate restTemplate;
    private final String inventoryServiceUrl;

    public InventoryRestAdapter(RestTemplate restTemplate,
                                 @Value("${inventory.service.url}") String inventoryServiceUrl) {
        this.restTemplate = restTemplate;
        this.inventoryServiceUrl = inventoryServiceUrl;
    }

    @Override
    public int getAvailableStock(String product) {
        try {
            ResponseEntity<InventoryStockResponse> response = restTemplate.getForEntity(
                    inventoryServiceUrl + "/api/inventory/stock?name={name}",
                    InventoryStockResponse.class,
                    product);
            InventoryStockResponse body = response.getBody();
            return body != null ? body.getStock() : 0;
        } catch (HttpClientErrorException.NotFound ex) {
            throw new IllegalArgumentException("Product not found in inventory: " + product);
        }
    }
}
