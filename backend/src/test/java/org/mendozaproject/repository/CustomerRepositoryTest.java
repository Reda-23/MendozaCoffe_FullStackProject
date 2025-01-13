package org.mendozaproject.repository;


import org.junit.jupiter.api.*;
import org.mendozaproject.models.Customer;
import org.mendozaproject.repos.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
public class CustomerRepositoryTest  {

    @Autowired
    private CustomerRepository customerRepository;

     private Customer customer;
     private List<Customer> customerList = new ArrayList<>();



    @BeforeEach
     void setUp(){
        customer = new Customer(1,"Reda","reda@gmail.com");
        customerRepository.save(customer);
        Customer customer2 = new Customer(2,"aziz","aziz@gmail.com");
        customerList = List.of(customer, customer2);
    }

    @Test
    public void giverCustomer_whenSaved_thenFetchById(){
        System.out.println("===============================");
        System.out.println(customer);
        System.out.println("===============================");
        Customer savedCustomer = customerRepository.findCustomerByCustomerId(1);
        System.out.println("===============================");
        System.out.println(savedCustomer);
        System.out.println("===============================");
        Assertions.assertNotNull(savedCustomer);
        Assertions.assertEquals(savedCustomer.getName(),customer.getName());
    }
    @Test
    public void givenCustomerList_whenSaved_checkIfNull(){
        customerList = customerRepository.saveAll(customerList);
        Assertions.assertNotNull(customerList);
        Assertions.assertEquals(customerList.size(),2);
    }

}
