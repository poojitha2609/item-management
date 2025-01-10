package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return itemRepository.save(item);
    }

    public Item updateItem(Long itemNo, Item updatedItem) {
        itemRepository.findById(itemNo).orElseThrow(() -> new EntityNotFoundException("Item with itemNo " + itemNo + " not found"));
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
            throw new IllegalArgumentException();
        }
        Item item = itemRepository.findById(itemNo)
                .orElseThrow(() -> new EntityNotFoundException("Item with ID " + itemNo + " not found."));
        itemRepository.delete(item);
        return String.format("Item with ID %d has been successfully deleted.", itemNo);
    }

    public void updateStock(Long itemNo, int newStockQty) {
        Item item = itemRepository.findById(itemNo).orElseThrow(() -> new EntityNotFoundException("Item with itemNo " + itemNo + " not found"));
        if (newStockQty < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        item.setStockQty(newStockQty);
        itemRepository.save(item);
    }


}
