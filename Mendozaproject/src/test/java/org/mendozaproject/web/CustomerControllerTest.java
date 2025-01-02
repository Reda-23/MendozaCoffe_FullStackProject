package org.mendozaproject.web;




import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.With;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mendozaproject.models.Customer;
import org.mendozaproject.services.CustomerService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper mapper;

    private Customer customer;


    @BeforeEach
    void setUp(){
        customer = new Customer(1,"Reda","reda@gmail.com");
    }

    @Test
    @WithMockUser
    public void saveCustomer_shouldCreateCx() throws Exception {
        Mockito.when(customerService.saveCustomer(Mockito.any(Customer.class))).thenReturn(customer);
        ResultActions response = mockMvc.perform(post("/v1/customer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customer)));
        response.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.customerId",is(customer.getCustomerId())))
                .andExpect(jsonPath("$.name",is(customer.getName())))
                .andExpect(jsonPath("$.email",is(customer.getEmail())));
        Mockito.verify(customerService).saveCustomer(Mockito.any(Customer.class));
    }

    @Test
    @WithMockUser
    public void getAllCustomers_shouldReturnAList() throws Exception {
        List<Customer> customerList = List.of(customer,new Customer(2,"simo","simo@gmail.com"));
        Mockito.when(customerService.getCustomers()).thenReturn(customerList);

        ResultActions response = mockMvc.perform(get("/v1/customer/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customerList)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(customerList.size())));

        Mockito.verify(customerService).getCustomers();
    }

    @Test
    @WithMockUser
    public void getCustomerById_shouldReturnCustomer() throws Exception {

        Mockito.when(customerService.searchCustomerById(1)).thenReturn(Optional.of(customer));

        ResultActions response = mockMvc.perform(get("/v1/customer/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customer)));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.customerId",is(customer.getCustomerId())))
                .andExpect(jsonPath("$.name",is(customer.getName())))
                .andExpect(jsonPath("$.email",is(customer.getEmail())));;

        Mockito.verify(customerService).searchCustomerById(1);

    }
    @Test
    @WithMockUser
    public void deleteCustomerById_shouldReturnOK() throws Exception {
        Mockito.doNothing().when(customerService).deleteCustomer(1);
        ResultActions response = mockMvc.perform(delete("/v1/customer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(customerService).deleteCustomer(1);
        }
}
