package com.akhil.w.orderservices.exception;

/**
 * akhil - 9/4/2021 - 3:50 PM
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order not found for order id : `" +id +"`.");
    }
}
