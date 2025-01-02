package org.mendozaproject.web;


import org.mendozaproject.models.Customer;
import org.mendozaproject.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/customer")
public class CustomerRESTController {

    private CustomerService customerService;

    public CustomerRESTController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){
        Customer savedCx = customerService.saveCustomer(customer);
        return new ResponseEntity<>(savedCx, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer customerId, @RequestBody Customer customer){
        Customer updateCustomer = customerService.updateCustomer(customer,customerId);
        return new ResponseEntity<>(updateCustomer,HttpStatus.OK);
    }


    @GetMapping("/customers")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = customerService.getCustomers();
        return new ResponseEntity<>(customers,HttpStatus.OK);
    }

    @GetMapping("/{cxId}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<Optional<Customer>> findCustomerByID(@PathVariable Integer cxId){
        Optional<Customer> customer = customerService.searchCustomerById(cxId);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer customerId){
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
