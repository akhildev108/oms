package com.akhil.w.orderservices.web.client.interfaces;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CUSTOMERSERVICES")
@Headers({ "Accept: application/json" })
public interface CustomerClient {

    @GetMapping("/customers/{id}/exists")
    Boolean customerExists(@PathVariable Long id);

}
