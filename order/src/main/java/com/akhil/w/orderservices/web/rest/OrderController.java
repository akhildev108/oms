package com.akhil.w.orderservices.web.rest;

import com.akhil.w.orderservices.domain.Order;
import com.akhil.w.orderservices.exception.OrderCanNotBeCreated;
import com.akhil.w.orderservices.exception.OrderNotFoundException;
import com.akhil.w.orderservices.service.OrderService;
import com.akhil.w.orderservices.web.client.interfaces.CustomerClient;
import com.akhil.w.orderservices.web.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * akhil - 10/8/2020 - 11:09 AM
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    private CustomerClient customerClient;

    public OrderController(OrderService orderService) {
        super();
        this.orderService = orderService;
    }

    @GetMapping("/customer/{customerId}")
    public List<Order> getOrdersForCustomer(@PathVariable("customerId") long customerId) {
        LOGGER.info("** OrderWebService :: customer id to get orders for ** " + customerId);
        return this.orderService.getOrdersForCustomer(customerId);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        LOGGER.info("*** get orders method *** ");
        return this.orderService.findAllOrders()
                .stream().map(OrderDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable("id") long id) {
        LOGGER.info("*** get order method for order id *** :: " + id);
        Order order = this.orderService.findOrder(id);
        if(order==null) {
            throw new OrderNotFoundException(id);
        }
        return OrderDto.toDto(order);
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody @NotNull OrderDto orderDto) {
        if(!customerExists(orderDto.getCustomerId())) {
            LOGGER.info("Customer not found for customer id `{}`", orderDto.getCustomerId());
            throw new OrderCanNotBeCreated("Customer not found for customer id `" + orderDto.getCustomerId() + "`");
        }
        return OrderDto.toDto(this.orderService.save(orderDto.toDao()));
    }

    @PutMapping("/{id}")
    public OrderDto updateOrder(@RequestBody @NotNull OrderDto orderDto, @PathVariable Long id) {
        LOGGER.info("*** updateOrder for order id *** :: " + id);
        Order order = this.orderService.findOrder(id);
        if(order==null) {
            throw new OrderNotFoundException(id);
        }
        order.setDescription(orderDto.getDescription());
        order.setEmail(orderDto.getEmail());
        order.setPhoneNumber(orderDto.getPhoneNumber());
        this.orderService.save(order);

        return OrderDto.toDto(order);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteOrder(@PathVariable("id") Long orderId) {
        //TODO: yet to implement
        return true;
    }

    private Boolean customerExists(Long customerId) {
        LOGGER.info("** going to check if customer exists for id :: " + customerId);
        return customerClient.customerExists(customerId);
    }

}
