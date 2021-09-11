package com.akhil.w.customerservices;

import com.akhil.w.customerservices.domain.Customer;
import com.akhil.w.customerservices.exception.CustomerNotFoundException;
import com.akhil.w.customerservices.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

/**
 * akhil - 9/5/2021 - 1:08 PM
 */
//@AutoConfigureMockMvc
@WebMvcTest
//@SpringBootTest
public class CustomerControllerTest {

    /*
     * We can @Autowire MockMvc because the WebApplicationContext provides an
     * instance/bean for us
     */
    @Autowired
    private MockMvc mockMvc;

    /*
     * Jackson mapper for Object -> JSON conversion
     */
    @Autowired
    private ObjectMapper objectMapper;

    /*
     * It only loads the beans solely required for testing the controller.
     */
    @MockBean
    private CustomerService customerService;

    Customer cust1 = new Customer("John", "Doe", "john.d@jd.test");
    Customer cust2 = new Customer("Jane", "Doe", "jane.d@jd.test");

    @Test
    public void getAllCustomers() throws Exception {
        Mockito.when(customerService.findAllCustomers()).thenReturn(List.of(cust1, cust2));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }
    @Test
    public void getCustomerById_success() throws Exception {
        Mockito.when(customerService.findCustomer(1L)).thenReturn(cust1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("John")));
    }
    @Test
    public void getCustomerById_ThrowsCustomerNotFoundException() throws Exception {
        //Mockito.when(customerService.findCustomer(1L)).thenReturn(cust1);
        //Mockito.when(customerService.findCustomer(1L)).thenReturn(Optional.empty());
        Mockito.when(customerService.findCustomer(1L)).thenReturn(null);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/customers/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("John")));

        Assertions.assertEquals(CustomerNotFoundException.class,
                resultActions.andReturn().getResolvedException().getClass());

        Assertions.assertTrue(resultActions.andReturn().getResolvedException().getMessage()
                .contains("Could find customer for id : `" + 1 +"'"));
    }
    @Test
    public void customerExists() throws Exception {
        Mockito.when(customerService.findCustomer(1L)).thenReturn(cust1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/customers/1/exists")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }
    @Test
    public void createCustomer() throws Exception {
        //Mockito.when(customerService.save(cust2)).thenReturn(cust2);
        Mockito.when(customerService.save(Mockito.any(Customer.class))).thenReturn(cust2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cust2));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("Jane")));
    }
    @Test
    public void updateCustomer() throws Exception {
        Mockito.when(customerService.findCustomer(1L)).thenReturn(cust1);

        cust1.setFirstName(cust1.getFirstName() + "-UPDATED");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cust1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("John-UPDATED")));
    }

    //@Test
    //TODO: yet to implement
    public void findOrdersForCustomer() throws Exception {
        Mockito.when(customerService.findCustomer(1L)).thenReturn(cust1);
    }

    //@Test
    //TODO: yet to implement
    public void deleteCustomer() throws Exception {
        Mockito.when(customerService.findCustomer(1L)).thenReturn(cust1);
    }

}
