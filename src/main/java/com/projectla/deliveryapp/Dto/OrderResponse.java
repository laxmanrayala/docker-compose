package com.projectla.deliveryapp.Dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
