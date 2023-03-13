package com.mafr.kafka.catalog.kafka;

import com.mafr.kafka.catalog.entity.CatalogEntity;
import com.mafr.kafka.catalog.services.CatalogService;
import com.mafr.kafka.orderservice.entity.OrderEntity;
import com.mafr.kafka.orderservice.entity.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CatalogConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogConsumer.class);
    @Autowired
    private CatalogService service;
    @Autowired
    private CatalogProducer catalogProducer;

    public CatalogConsumer(CatalogProducer catalogProducer, CatalogService service) {
        this.catalogProducer = catalogProducer;
        this.service = service;
    }

    @KafkaListener(topics = "${spring.kafka.topic.catalog}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent event, ConsumerRecord<String, OrderEvent> payload){
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        LOGGER.info("topic = {}", payload.topic());
        LOGGER.info("value = {}", event.getOrder());

        try {
            CatalogEntity response = service.getByID(event.getOrder().getCatalogID());
            LOGGER.info("Success order data with ID {} : {}", event.getOrder().getCatalogID(), response);

            response.setQuantity(event.getOrder().getQuantity());
            String result = service.order(response);
            LOGGER.info("{} Success to order...", response.getName());

            OrderEntity userOrder = event.getOrder();
            userOrder.setStatusUser("0");
            userOrder.setStatusOrder("Success");
            OrderEvent eventOrder = new OrderEvent();
            eventOrder.setOrder(userOrder);
            catalogProducer.sendMessage(eventOrder);
            LOGGER.info("User Order success...");

        } catch (Exception e) {

            OrderEntity userOrder = event.getOrder();
            userOrder.setStatusUser("0");
            userOrder.setStatusOrder("Failed");
            OrderEvent eventOrder = new OrderEvent();
            eventOrder.setOrder(userOrder);
            catalogProducer.sendMessage(eventOrder);

            LOGGER.error("Failed order data with ID {}", event.getOrder().getUserID());
            LOGGER.error("Error : {}, cause: {}", e.getMessage(), e.getCause());
        }
    }
}