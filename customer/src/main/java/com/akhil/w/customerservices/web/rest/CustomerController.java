package com.akhil.w.customerservices.web.rest;

import com.akhil.w.customerservices.domain.Customer;
import com.akhil.w.customerservices.exception.CustomerNotFoundException;
import com.akhil.w.customerservices.service.CustomerService;
import com.akhil.w.customerservices.web.client.interfaces.OrderClient;
import com.akhil.w.customerservices.web.dto.CustomerDto;
import com.akhil.w.customerservices.web.dto.CustomerOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * akhil - 10/2/2020 - 11:52 AM
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @Autowired
    private OrderClient orderClient;

    public CustomerController(CustomerService service) {
        super();
        this.customerService = service;
    }

    @GetMapping("/{id}/orders")
    public List<CustomerOrder> getCustomerOrders(@PathVariable("id") long customerId) {
        LOGGER.info("going to get orders for a customer with id :: " + customerId);
        return orderClient.getOrdersForCustomer(customerId);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return this.customerService.findAllCustomers()
                .stream().map(CustomerDto::toDto).collect(Collectors.toList());
    }

    @Operation(summary = "Get a customer by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the customer",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = CustomerDto.class))}),
            @ApiResponse(responseCode = "404", description = "Customer not found for supplied id",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable("id") Long customerId) {
        Customer customer = this.customerService.findCustomer(customerId);
        if(customer==null) {
            throw new CustomerNotFoundException(customerId);
        }
        return CustomerDto.toDto(customer);
    }

    @GetMapping("/{id}/exists")
    public Boolean customerExists(@PathVariable("id") Long customerId) {
        LOGGER.info("going to check if customer exists for id :: " + customerId);
        return this.customerService.findCustomer(customerId)!=null;
    }

    @PostMapping
    public CustomerDto createCustomer(@RequestBody @NotNull CustomerDto customerDto) {
        return CustomerDto.toDto(this.customerService.save(customerDto.toDao()));
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@RequestBody @NotNull CustomerDto customerDto, @PathVariable Long id) {
        Customer customer = this.customerService.findCustomer(id);
        if(customer==null) {
            throw new CustomerNotFoundException(id);
        }
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        this.customerService.save(customer);

        return CustomerDto.toDto(customer);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteCustomer(@PathVariable("id") Long customerId) {
        //TODO: yet to implement
        //Business uses cases when a customer can not deleted from the system
        //and should be soft delete or hard delete the customer
        return true;
    }
}
