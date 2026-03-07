package com.projectla.deliveryapp.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartResponse {

    private Long cartId;
    private Long userId;
    private List<CartItemResponse> items;
}
