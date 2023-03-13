package com.mafr.kafka.orderservice.services;

import com.mafr.kafka.orderservice.entity.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> getAll();

    OrderEntity getByID(Long id);

    String order(OrderEntity param) throws Exception;

    void setStatusUser(OrderEntity order);

    void setStatusOrder(OrderEntity order);
}
