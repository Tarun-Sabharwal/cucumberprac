package org.example.inventory.in.rest;

import org.example.inventory.core.domain.InventoryItem;
import org.example.inventory.core.usecase.InventoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryUseCase inventoryUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryItem add(@RequestBody InventoryItem item) {
        return inventoryUseCase.addItem(item);
    }

    @GetMapping
    public List<InventoryItem> getAll() {
        return inventoryUseCase.getAllItems();
    }

    @GetMapping("/{id}")
    public InventoryItem getById(@PathVariable Long id) {
        return inventoryUseCase.getItem(id)
                .orElseThrow(() -> new RuntimeException("Item not found: " + id));
    }

    @GetMapping("/stock")
    public InventoryItem getStockByName(@RequestParam String name) {
        return inventoryUseCase.getItemByName(name)
                .orElseThrow(() -> new RuntimeException("Item not found: " + name));
    }

    @PutMapping("/deduct")
    public InventoryItem deductStock(@RequestParam String name, @RequestParam int quantity) {
        return inventoryUseCase.deductStock(name, quantity);
    }

    @PutMapping("/{id}/stock")
    public InventoryItem updateStock(@PathVariable Long id, @RequestParam int stock) {
        return inventoryUseCase.updateStock(id, stock);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        inventoryUseCase.removeItem(id);
    }
}
