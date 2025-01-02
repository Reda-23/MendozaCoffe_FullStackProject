package org.mendozaproject.services;

import org.mendozaproject.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {


    Customer saveCustomer(Customer customer);
    Optional<Customer> searchCustomerById(Integer customerId);
    void  deleteCustomer(Integer customerId);
    List<Customer> getCustomers();
    Customer updateCustomer(Customer customer, Integer customerId);


}
