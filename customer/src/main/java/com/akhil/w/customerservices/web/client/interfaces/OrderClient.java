package com.akhil.w.customerservices.web.client.interfaces;

import com.akhil.w.customerservices.web.dto.CustomerOrder;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * akhil - 10/8/2020 - 1:04 PM
 */
@FeignClient(value = "ORDERSERVICES")
@Headers({ "Accept: application/json" })
public interface OrderClient {

    @GetMapping("/orders/customer/{customerId}")
    List<CustomerOrder> getOrdersForCustomer(@PathVariable Long customerId);
}
