package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final WebClient.Builder webClientBuilder;


    public ItemService(ItemRepository itemRepository, WebClient.Builder webClientBuilder) {
        this.itemRepository = itemRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<Item> getItem() {
        return itemRepository.findAll();
    }

    public Item getItemByItemNum(Long itemNo) {
        return itemRepository.findById(itemNo).orElseThrow(() -> new EntityNotFoundException("Item not found"));
    }

    public Item saveItem(Item item) {
        if (itemRepository.existsByItemNo(item.getItemNo())) {
            throw new IllegalArgumentException(String.format("Item with itemNo %d already exists.", item.getItemNo()));
        }
        return itemRepository.save(item);
    }


    public Item updateItem(Long itemNo, Item updatedItem) {
        itemRepository.findById(itemNo).orElseThrow(() -> new EntityNotFoundException(String.format("Item with %d not found", itemNo)));
        updatedItem.setItemNo(itemNo);
        return itemRepository.save(updatedItem);
    }

    public String deleteItem(Long itemNo) {
        boolean isItemInOrder = Boolean.TRUE.equals(webClientBuilder
                .baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder.path("/sales-orders/item/{itemNo}").build(Map.of("itemNo", itemNo)))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());

        if (Boolean.TRUE.equals(isItemInOrder)) {
            throw new IllegalArgumentException("Item cannot be deleted");
        }
        Item item = itemRepository.findById(itemNo)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Item with ID %d not found.", itemNo)));
        itemRepository.delete(item);
        return String.format("Item with ID %d has been successfully deleted.", itemNo);
    }

    public void updateStock(Long itemNo, Integer newStockQty) {
        Item item = itemRepository.findById(itemNo).orElseThrow(() -> new EntityNotFoundException(String.format("Item with itemNo %d not found", itemNo)));
        if (newStockQty == null || newStockQty < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative and cannot be zero");
        }
        item.setStockQty(newStockQty);
        itemRepository.save(item);
    }


}
