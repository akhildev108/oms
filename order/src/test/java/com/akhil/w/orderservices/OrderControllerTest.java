package com.akhil.w.orderservices;

import com.akhil.w.orderservices.domain.Order;
import com.akhil.w.orderservices.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * akhil - 9/5/2021 - 4:20 PM
 */
//@AutoConfigureMockMvc
@WebMvcTest
//@SpringBootTest
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    Order order1 = new Order(1L, "2 512 GB SSDs", "john.d@test.test", "000-000-0001");
    Order order2 = new Order(2L, "2 LG monitors and 1 thinkpad", "jane.d@test.test", "000-000-0002");

    @Test
    public void getAllOrders() throws Exception {
        Mockito.when(orderService.findAllOrders()).thenReturn(List.of(order1, order2));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[0].description", is("2 512 GB SSDs")))
                .andExpect(jsonPath("$[1].customerId", is(2)))
                .andExpect(jsonPath("$[1].description", is("2 LG monitors and 1 thinkpad")));
    }
    @Test
    public void getOrderById_success() throws Exception {
        Mockito.when(orderService.findOrder(1L)).thenReturn(order1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/orders/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.description", is("2 512 GB SSDs")));
    }

    //@Test
    //TODO: yet to make this test case pass
    public void createOrder_whenCustomerExists() throws Exception {
        Mockito.when(orderService.save(order1)).thenReturn(order1);
        //Mockito.when(customerClient.customerExists(1L)).thenReturn(true);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.description", is("2 512 GB SSDs")));
    }
    @Test
    public void updateOrder() throws Exception {
        Mockito.when(orderService.findOrder(1L)).thenReturn(order1);

        order1.setDescription(order1.getDescription() + "-UPDATED");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.customerId", is(1)))
                .andExpect(jsonPath("$.description", is("2 512 GB SSDs-UPDATED")));
    }

}
