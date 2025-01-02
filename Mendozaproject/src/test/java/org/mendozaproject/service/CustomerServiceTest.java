package org.mendozaproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mendozaproject.models.Customer;
import org.mendozaproject.repos.CustomerRepository;
import org.mendozaproject.services.impl.CustomerServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Optional;


@SpringBootTest
public class CustomerServiceTest {


    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    private Customer customer;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1,"Reda","reda@gmail.com");
    }


    // check if the save method is working when given a valid customer
    @Test
    public void givenCustomerCheckIfSaved(){
        //Given
        Customer savedCx = new Customer(1,"Reda","reda@gmail.com");
        // Mock The Calls
        Mockito.when(customerRepository.save(customer)).thenReturn(savedCx);
        //When
        Customer customer1 = customerService.saveCustomer(customer);
        //Then
        Assertions.assertEquals(customer1,savedCx);
        Assertions.assertEquals(customer1.getName(),savedCx.getName());
        Assertions.assertNotNull(savedCx);
    }

    @Test
    public void shouldReturnCustomerByHisId(){
        //Mock repository behavior
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Optional<Customer> cx = customerService.searchCustomerById(1);
        Assertions.assertTrue(cx.isPresent(),"customer is present ");
    }

    @Test
    public void shouldThrowAnExceptionWhenCalled(){
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.empty());
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class
                ,()-> customerService.searchCustomerById(1));
        Assertions.assertEquals(runtimeException.getMessage(),"customer is not found");
    }

    // My implementation
    @Test
    public void testUpdateEmployee_whenValid_thenUpdate(){
        Mockito.when(customerRepository.findCustomerByCustomerId(1)).thenReturn(customer);
        customer.setName("driss");
        customer.setEmail("driss@gmail.com");
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        Customer updateCustomer = customerService.updateCustomer(customer,1);

        Assertions.assertEquals(updateCustomer,customer);
        Assertions.assertEquals(updateCustomer.getName(),"driss");
    }


    // Chatgpt Implementation
 /*   @Test
    public void testUpdateCustomer_whenValid_thenUpdate() {
        // Arrange
        Mockito.when(customerRepository.findCustomerByCustomerId(1)).thenReturn(customer);
        customer.setName("driss");
        customer.setEmail("driss@gmail.com");
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        Customer updatedCustomer = customerService.updateCustomer(customer, 1);

        // Assert
        Assertions.assertNotNull(updatedCustomer, "Updated customer should not be null");
        Assertions.assertEquals(customer, updatedCustomer, "The returned customer should match the updated customer");
        Assertions.assertEquals("driss", updatedCustomer.getName(), "Customer name should be updated");
        Assertions.assertEquals("driss@gmail.com", updatedCustomer.getEmail(), "Customer email should be updated");

        // Verify interactions
        Mockito.verify(customerRepository).findCustomerByCustomerId(1);
        Mockito.verify(customerRepository).save(customer);
    }*/


    @Test
    public void testDeleteCustomer_whenExist_thenDelete() {
        Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(new Customer()));

        customerService.deleteCustomer(1);

        Mockito.verify(customerRepository).findById(1);
        Mockito.verify(customerRepository).deleteById(1);

    }


}
