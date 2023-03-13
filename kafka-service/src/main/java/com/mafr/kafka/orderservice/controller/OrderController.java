package com.mafr.kafka.orderservice.controller;

import com.mafr.kafka.orderservice.entity.OrderEntity;
import com.mafr.kafka.orderservice.entity.OrderEvent;
import com.mafr.kafka.orderservice.kafka.OrderProducer;
import com.mafr.kafka.orderservice.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService service;
    @Autowired
    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer, OrderService service) {
        this.orderProducer = orderProducer;
        this.service = service;
    }

    @GetMapping
    public List<OrderEntity> getAll() {
        List<OrderEntity> response = service.getAll();
        log.info("Success get data");
        return response;
    }

    @GetMapping("/{id}")
    public OrderEntity getByID(@PathVariable Long id) {
        OrderEntity response = service.getByID(id);
        log.info("Success get data with ID {}", id);
        return response;
    }

    @PostMapping
    public String order(@RequestBody OrderEntity param) {
        try {
            String response = service.order(param);
            log.info("Success {}", param);

            OrderEvent event = new OrderEvent();
            event.setOrder(param);

            orderProducer.sendMessageCatalog(event);
            orderProducer.sendMessage(event);

            return response;
        } catch (Exception e) {
            log.error("Failed {}", e.getMessage());
            return "Failed : " + e.getMessage() + ", " + e.getCause();
        }
    }

}