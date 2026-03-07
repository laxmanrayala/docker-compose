package com.projectla.deliveryapp.Dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemResponse {

    private String productName;
    private Double price;
    private Integer quantity;
}
