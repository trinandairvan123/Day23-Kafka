package com.mafr.kafka.orderservice.services.implementation;

import com.mafr.kafka.orderservice.entity.OrderEntity;
import com.mafr.kafka.orderservice.repository.OrderRepository;
import com.mafr.kafka.orderservice.services.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderImpl implements OrderService {

    @PersistenceContext
    private EntityManager entityManager;
    private final OrderRepository repository;

    @Override
    public List<OrderEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public OrderEntity getByID(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public String order(OrderEntity param) throws Exception {
        repository.save(param);
        return "Success";
    }

    @Override
    public void setStatusUser(OrderEntity order) {
        repository.setStatusUser(order.getId(), order.getStatusUser());
    }

    @Override
    public void setStatusOrder(OrderEntity order) {
        repository.setStatusOrder(order.getId(), order.getStatusOrder());
    }

}