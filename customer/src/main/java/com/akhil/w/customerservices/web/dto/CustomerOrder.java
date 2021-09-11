package com.akhil.w.customerservices.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * akhil - 10/7/2020 - 3:57 PM
 */
@Data
public class CustomerOrder implements Serializable {

    private long id;
    private long customerId;
    private String description;
    private String email;
    private String phoneNumber;
}
