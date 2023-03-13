package com.mafr.kafka.orderservice.kafka;

import com.mafr.kafka.orderservice.entity.OrderEvent;
import com.mafr.kafka.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);
    @Autowired
    private OrderService service;

    public UserConsumer(OrderService service) {
        this.service = service;
    }

    @KafkaListener(topics = "${spring.kafka.topic.order}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent event, ConsumerRecord<String, OrderEvent> payload){
        LOGGER.info("topic = {}", payload.topic());
        LOGGER.info("value = {}", event.getOrder());
        LOGGER.info(String.format("Order event received in stock service => %s", event));

        if (Objects.equals(event.getOrder().getStatusOrder(), "0")) {
            service.setStatusUser(event.getOrder());
        }

        if (Objects.equals(event.getOrder().getStatusUser(), "0")) {
            service.setStatusOrder(event.getOrder());
        }
    }
}