package com.akhil.w.orderservices.exception;

/**
 * akhil - 9/5/2021 - 3:42 PM
 */
public class OrderCanNotBeCreated extends RuntimeException {
    public OrderCanNotBeCreated(String message) {
        super("Order can not be created because: " + message);
    }
}
