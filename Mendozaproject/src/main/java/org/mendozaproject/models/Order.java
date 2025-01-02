package org.mendozaproject.models;

import jakarta.persistence.*;
import lombok.ToString;


import java.util.*;

@Entity
@Table(name = "ORDERS")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Date orderDate;
    private int numberOfItems;
    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "item_id" )
    private Map<Item,Integer> items = new HashMap<>();
    @ManyToOne
    private Waiter waiter;
    private double orderPrice;

    public Order() {
    }

    public Order(Integer orderId, Date orderDate, int numberOfItems, Map<Item, Integer> items, Waiter waiter, double orderPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.numberOfItems = numberOfItems;
        this.items = items;
        this.waiter = waiter;
        this.orderPrice = orderPrice;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }
}

