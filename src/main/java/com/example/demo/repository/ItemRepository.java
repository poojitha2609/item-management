package com.example.demo.repository;

import com.example.demo.dto.ItemDTO;
import com.example.demo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByItemNo(Long itemNo);
}
