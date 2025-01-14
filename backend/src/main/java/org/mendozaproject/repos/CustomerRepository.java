package org.mendozaproject.repos;

import org.mendozaproject.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findCustomerByCustomerId(Integer customerId);
}
