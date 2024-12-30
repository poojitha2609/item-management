package com.example.demo.service;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItem() {
        return itemRepository.findAll();
    }

    public Item getItemByItemNum(Long id) {
        return itemRepository.findById(id).get();
    }


    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long itemno, Item updatedItem) {
        updatedItem.setItemNo(itemno);
        return itemRepository.save(updatedItem);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

}
