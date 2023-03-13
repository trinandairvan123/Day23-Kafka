package com.mafr.kafka.user.kafka;

import com.mafr.kafka.orderservice.entity.OrderEntity;
import com.mafr.kafka.orderservice.entity.OrderEvent;
import com.mafr.kafka.user.entity.UserEntity;
import com.mafr.kafka.user.services.UserService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
    @Autowired
    private UserService service;
    @Autowired
    private UserProducer userProducer;

    public OrderConsumer(UserProducer userProducer, UserService service) {
        this.userProducer = userProducer;
        this.service = service;
    }

    @KafkaListener(topics = "${spring.kafka.topic.user}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent event, ConsumerRecord<String, OrderEvent> payload){
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        LOGGER.info("topic = {}", payload.topic());
        LOGGER.info("value = {}", event.getOrder());

        try {
            UserEntity response = service.getByID(event.getOrder().getUserID());
            LOGGER.info("Success order data with ID {} : {}", event.getOrder().getUserID(), response);

            String result = service.order(event.getOrder().getUserID());
            LOGGER.info("{} Success to order...", response.getName());

            OrderEntity userOrder = event.getOrder();
            userOrder.setStatusUser("Success");
            userOrder.setStatusOrder("0");
            OrderEvent eventOrder = new OrderEvent();
            eventOrder.setOrder(userOrder);
            userProducer.sendMessage(eventOrder);
            LOGGER.info("User Order success...");

        } catch (Exception e) {

            OrderEntity userOrder = event.getOrder();
            userOrder.setStatusUser("Failed");
            userOrder.setStatusOrder("0");
            OrderEvent eventOrder = new OrderEvent();
            eventOrder.setOrder(userOrder);
            userProducer.sendMessage(eventOrder);

            LOGGER.error("Failed order data with ID {}", event.getOrder().getUserID());
            LOGGER.error("Error : {}, cause: {}", e.getMessage(), e.getCause());
        }
    }
}