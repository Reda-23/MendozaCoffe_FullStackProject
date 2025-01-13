package org.mendozaproject.services;

import org.mendozaproject.models.Item;
import org.mendozaproject.models.Order;
import org.mendozaproject.models.Waiter;

import java.util.List;

public interface OrderService {





    Order createOrder(Integer WaiterId);

    Order addItemToOrder(Integer itemId, Integer waiterId);

    Order removeItemFromOrder(Integer orderId, Integer itemId);

    List<Order> ordersHistory();
}
