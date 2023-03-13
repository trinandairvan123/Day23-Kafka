package com.mafr.kafka.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String message = "Status is in pending state";
    private String status = "PENDING";
    private OrderEntity order;
}