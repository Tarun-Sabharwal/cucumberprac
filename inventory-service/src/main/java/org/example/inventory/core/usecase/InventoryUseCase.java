package org.example.inventory.core.usecase;

import org.example.inventory.common.UseCase;
import org.example.inventory.core.domain.InventoryItem;
import org.example.inventory.core.port.ManageInventoryPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@UseCase
public class InventoryUseCase {

    @Autowired
    private ManageInventoryPort manageInventoryPort;

    public InventoryItem addItem(InventoryItem item) {
        if (item.getStock() < 1) {
            throw new IllegalArgumentException("Stock should be greater than 0");
        }
        return manageInventoryPort.save(item);
    }

    public Optional<InventoryItem> getItem(Long id) {
        return manageInventoryPort.findById(id);
    }

    public List<InventoryItem> getAllItems() {
        return manageInventoryPort.findAll();
    }

    public InventoryItem updateStock(Long id, int newStock) {
        if (newStock < 1) {
            throw new IllegalArgumentException("Stock should be greater than 0");
        }
        InventoryItem item = manageInventoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found: " + id));
        item.setStock(newStock);
        return manageInventoryPort.save(item);
    }

    public InventoryItem deductStock(String name, int quantity) {
        InventoryItem item = manageInventoryPort.findByName(name)
                .orElseThrow(() -> new RuntimeException("Item not found: " + name));
        if (item.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }
        item.setStock(item.getStock() - quantity);
        return manageInventoryPort.save(item);
    }

    public void removeItem(Long id) {
        manageInventoryPort.deleteById(id);
    }
}
