package com.projectla.deliveryapp.Dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest {

    private Long productId;
    private Integer quantity;
}
