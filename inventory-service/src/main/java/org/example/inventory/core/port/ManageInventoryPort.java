package org.example.inventory.core.port;

import org.example.inventory.core.domain.InventoryItem;
import java.util.List;
import java.util.Optional;

public interface ManageInventoryPort {
    InventoryItem save(InventoryItem item);
    Optional<InventoryItem> findById(Long id);
    Optional<InventoryItem> findByName(String name);
    List<InventoryItem> findAll();
    void deleteById(Long id);
}
