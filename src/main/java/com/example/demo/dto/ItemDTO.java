package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDTO {
    private String name;
    private Long itemNo;
    private String description;
    private float price;
    private Integer stockQty;

}
