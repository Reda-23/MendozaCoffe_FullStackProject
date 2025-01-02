package org.mendozaproject.services.impl;

import org.mendozaproject.models.Customer;
import org.mendozaproject.repos.CustomerRepository;
import org.mendozaproject.services.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {


    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> searchCustomerById(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) throw new RuntimeException("customer is not found");
        return customer;
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) throw new RuntimeException("customer is not found");
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer, Integer customerId) {
        Customer cx = customerRepository.findCustomerByCustomerId(customerId);
        if (customer == null)  throw new RuntimeException("customer is not found");
        cx.setName(customer.getName());
        cx.setEmail(customer.getEmail());
        var updCx = customerRepository.save(cx);

        return updCx;
    }


}
