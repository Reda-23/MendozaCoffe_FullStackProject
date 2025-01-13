package org.mendozaproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.mendozaproject.models.Item;
import org.mendozaproject.models.Order;
import org.mendozaproject.models.Waiter;
import org.mendozaproject.repos.ItemRepository;
import org.mendozaproject.repos.OrderRepository;
import org.mendozaproject.repos.WaiterRepository;
import org.mendozaproject.services.OrderService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;



@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private WaiterRepository waiterRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ItemRepository itemRepository, WaiterRepository waiterRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.waiterRepository = waiterRepository;
    }

    @Override
    public Order createOrder(Integer waiterId) {
        Waiter waiter = waiterRepository.findWaiterByWaiterId(waiterId);
        if (waiter == null) throw new RuntimeException("waiter is not found with this id: " +waiterId );
       Order order = new Order();
       order.setOrderDate(new Date());
       order.setWaiter(waiter);
       orderRepository.save(order);
       return order;
    }

    @Override
    public Order addItemToOrder(Integer orderId,Integer itemId){
        Order order = orderRepository.findOrderByOrderId(orderId);
        Item item = itemRepository.findItemByItemId(itemId);
        if (item == null) throw new RuntimeException("item is not found with this id: " + itemId);
        var orderItems = order.getItems();
        orderItems.put(item,orderItems.getOrDefault(item,0)+1);
        double orderPrice = 0.0;
        orderPrice = orderItems.entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
        int totalItems = orderItems.values().stream().mapToInt(p -> p.intValue()).sum();
        order.setNumberOfItems(totalItems);
        order.setOrderPrice(orderPrice);
        var savedOrder = orderRepository.save(order);
        return savedOrder;
    }


    @Override
    public Order removeItemFromOrder(Integer orderId, Integer itemId){
        Order order = orderRepository.findOrderByOrderId(orderId);
        Item item = itemRepository.findItemByItemId(itemId);
        var items = order.getItems();
        if (item == null) throw new RuntimeException("item is not found with this id: " + itemId);
        if (items.containsKey(item)){
            int quantity = items.get(item);
            if (quantity > 1){
                items.put(item,quantity-1);
            }else {
                items.remove(item);
            }
        }
        double orderPrice = 0.0;
        orderPrice = items.entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
        order.setOrderPrice(orderPrice);
        var updOrder = orderRepository.save(order);
        return updOrder;
    }


    @Override
    public List<Order> ordersHistory(){
        List<Order> orders = orderRepository.findAll();
        return orders;
    }
}
