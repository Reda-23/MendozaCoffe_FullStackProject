package org.mendozaproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mendozaproject.enums.Schedule;
import org.mendozaproject.models.Item;
import org.mendozaproject.models.Order;
import org.mendozaproject.models.Waiter;
import org.mendozaproject.repos.ItemRepository;
import org.mendozaproject.repos.OrderRepository;
import org.mendozaproject.repos.WaiterRepository;
import org.mendozaproject.services.impl.OrderServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Date;
import java.util.HashMap;


@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private WaiterRepository waiterRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private Item item;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
         order = new Order(1,new Date(),0,new HashMap<>(),new Waiter(),0);
         item = new Item(1,"Coffee",12,"the best in the town");
    }


    @Test
    public void givenAnId_andValidOrder_saveOrder(){
        Waiter waiter = new Waiter(1,"Driss","0847382728", Schedule.MORNING);
        Order order = new Order(1,new Date(),0,new HashMap<>(),waiter,0);
        Mockito.when(waiterRepository.findWaiterByWaiterId(1)).thenReturn(waiter);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        Order order1 = orderService.createOrder(1);
        order1.setOrderId(1);

        Assertions.assertNotNull(order1);
        Assertions.assertEquals(order1.getOrderPrice(),order.getOrderPrice());

        Mockito.verify(orderRepository).save(Mockito.any(Order.class));
        Mockito.verify(waiterRepository).findWaiterByWaiterId(1);
    }

    @Test
    public void givenANonValidId_shouldThrowAnException(){
        Mockito.when(waiterRepository.findWaiterByWaiterId(1)).thenReturn(null);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,()-> orderService.createOrder(1),"waiter is not found with this id: " +1);
        Assertions.assertEquals(exception.getMessage(),"waiter is not found with this id: " +1);

        Mockito.verify(waiterRepository).findWaiterByWaiterId(1);
    }

    @Test
    public void givenAnItem_checkIfAddedToOrder(){
        Mockito.when(orderRepository.findOrderByOrderId(1)).thenReturn(order);
        Mockito.when(itemRepository.findItemByItemId(1)).thenReturn(item);
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        Order order1 = orderService.addItemToOrder(1,1);


        Assertions.assertEquals(order1.getItems().size(),1,"the order item size is equal");
        Assertions.assertEquals(order1.getItems().isEmpty(),false);

        Mockito.verify(orderRepository).findOrderByOrderId(1);
        Mockito.verify(itemRepository).findItemByItemId(1);
    }

    @Test
    public void checkIfItemIsRemovedFromOrder(){
        order.getItems().put(item,1);

        Mockito.when(orderRepository.findOrderByOrderId(1)).thenReturn(order);
        Mockito.when(itemRepository.findItemByItemId(1)).thenReturn(item);
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        Order order1 = orderService.removeItemFromOrder(1,1);

        Assertions.assertEquals(order1.getItems().isEmpty(),true);


        Mockito.verify(orderRepository).findOrderByOrderId(1);
        Mockito.verify(itemRepository).findItemByItemId(1);
        Mockito.verify(orderRepository).save(order1);




    }
}
