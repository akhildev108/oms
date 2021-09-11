package com.akhil.w.customerservices.exception;

/**
 * akhil - 9/4/2021 - 12:34 AM
 */
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super("Could find customer for id : `" +id +"'");
    }

}
