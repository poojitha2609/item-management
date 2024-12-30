package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

    @GetMapping("/items/{id}")
    public String getItemsByItemNum(@PathVariable("id") Long id) {
        try {
            Item item = itemService.getItemByItemNum(id);
            return item.toString();
        } catch (NoSuchElementException e) {
            return "Item with ID " + id + " not found";
        }
    }

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) {
        return itemService.saveItem(item);
    }

    @PutMapping("/items/{itemno}")
    public Item updateItem(@PathVariable Long itemno, @RequestBody Item updatedItem) {
        return itemService.updateItem(itemno, updatedItem);
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
