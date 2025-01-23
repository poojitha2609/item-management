package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public List<Item> items() {
        return itemService.getItem();
    }

    @GetMapping("/items/{itemNo}")
    public ResponseEntity<Item> getItem(@PathVariable Long itemNo) {
        Item item = itemService.getItemByItemNum(itemNo);
        return ResponseEntity.ok(item);
    }

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PutMapping("/items/{itemNo}")
    public ResponseEntity<Item> updateItem(@PathVariable Long itemNo, @RequestBody Item updatedItem) {
        return ResponseEntity.ok(itemService.updateItem(itemNo, updatedItem));
    }

    @DeleteMapping("/items/{itemNo}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemNo) {
        String deleteItem = itemService.deleteItem(itemNo);
        return ResponseEntity.ok(deleteItem);
    }

    @PutMapping("/items/{itemNo}/updateStock")
    public ResponseEntity<String> updateStock(@PathVariable Long itemNo, @RequestBody Map<String, Integer> stockUpdate) {
        Integer newStockQty = stockUpdate.get("newStockQty");
        itemService.updateStock(itemNo, newStockQty);
        return ResponseEntity.ok("Stock updated successfully.");
    }

}
