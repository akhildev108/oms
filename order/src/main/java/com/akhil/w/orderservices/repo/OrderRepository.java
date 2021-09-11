package com.akhil.w.orderservices.repo;

import com.akhil.w.orderservices.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * akhil - 9/4/2021 - 3:47 PM
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long id);

}