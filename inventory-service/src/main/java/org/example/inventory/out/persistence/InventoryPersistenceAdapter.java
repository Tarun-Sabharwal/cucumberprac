package org.example.inventory.out.persistence;

import org.example.inventory.core.domain.InventoryItem;
import org.example.inventory.core.port.ManageInventoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InventoryPersistenceAdapter implements ManageInventoryPort {

    @Autowired
    private InventoryJpaRepository repository;

    @Override
    public InventoryItem save(InventoryItem item) {
        return toDomain(repository.save(toEntity(item)));
    }

    @Override
    public Optional<InventoryItem> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<InventoryItem> findByName(String name) {
        return repository.findByName(name).map(this::toDomain);
    }

    @Override
    public List<InventoryItem> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private InventoryEntity toEntity(InventoryItem item) {
        InventoryEntity entity = new InventoryEntity();
        entity.setId(item.getId());
        entity.setName(item.getName());
        entity.setStock(item.getStock());
        return entity;
    }

    private InventoryItem toDomain(InventoryEntity entity) {
        return new InventoryItem(entity.getId(), entity.getName(), entity.getStock());
    }
}
