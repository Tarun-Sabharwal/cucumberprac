package org.example.inventory.out.persistence;

import org.example.inventory.common.DomainService;
import org.example.inventory.core.domain.InventoryItem;
import org.example.inventory.core.port.ManageInventoryPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@DomainService
public class InventoryPersistenceAdapter implements ManageInventoryPort {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public InventoryItem save(InventoryItem item) {
        return inventoryRepository.save(item);
    }

    @Override
    public Optional<InventoryItem> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public Optional<InventoryItem> findByName(String name) {
        return inventoryRepository.findByName(name);
    }

    @Override
    public List<InventoryItem> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }
}
