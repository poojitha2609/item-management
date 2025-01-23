package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "item")
@Data
public class Item {
    @Id
    @Column(name = "itemno" , nullable = false , unique = true )
    @Min(value = 1, message = "itemno must be greater than or equal to 1")
    private Long itemNo;

    @Column(name = "name")
    @NotNull(message = "item name cannot be null")
    @Size(min = 3, max = 50, message = "item name must be between 3 and 50 characters")
    private String name;

    @Column(name = "description")
    @NotNull(message = "Item description cannot be null")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Item price cannot be null")
    private float price;

    @Column(name = "stockqty")
    @NotNull(message = "Item quantity cannot be null")
    @Min(value = 1 , message = "quantity should be grater than one")
    private Integer stockQty;

}
