package org.mendozaproject.repos;


import org.mendozaproject.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {


    Order findOrderByOrderId(Integer orderId);

}
