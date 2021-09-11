package com.akhil.w.orderservices.service;

import com.akhil.w.orderservices.domain.Order;
import com.akhil.w.orderservices.repo.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * akhil - 9/4/2021 - 3:54 PM
 */
@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        super();
        this.repository = repository;
    }

    public List<Order> findAllOrders() {
        return this.repository.findAll();
    }

    public Order findOrder(long id) {
        return this.repository.findById(id).orElse(null);
    }

    public List<Order> getOrdersForCustomer(Long customerId) {
        return this.repository.findByCustomerId(customerId);
    }

    public Order save(Order order) {
        return this.repository.save(order);
    }
}
