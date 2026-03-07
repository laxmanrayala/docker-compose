package com.projectla.deliveryapp.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponse {

    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
