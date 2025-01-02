package org.mendozaproject.repository;



import org.junit.jupiter.api.*;
import org.mendozaproject.enums.Schedule;
import org.mendozaproject.models.Order;
import org.mendozaproject.models.Waiter;
import org.mendozaproject.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private Order order;


    @BeforeEach
    void setUp() {
        order = new Order(null,new Date(),3,new HashMap<>(),new Waiter(1,"Driss","06747382928", Schedule.MORNING),40);
        order = orderRepository.save(order);
    }

   @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    public void givenValidOrder_whenSaved_thenReturnOrder(){
        Order order1 = orderRepository.findOrderByOrderId(order.getOrderId());

        Assertions.assertNotNull(order1,"order should not be null");
        Assertions.assertEquals(order1.getOrderPrice(),order.getOrderPrice());

    }
}
