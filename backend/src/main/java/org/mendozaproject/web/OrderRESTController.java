package org.mendozaproject.web;

import org.mendozaproject.models.Order;
import org.mendozaproject.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/order")
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class OrderRESTController {

    private OrderService orderService;

    public OrderRESTController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{waiterId}")
    public ResponseEntity<Order> createOrder(@PathVariable Integer waiterId){
        Order order = orderService.createOrder(waiterId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}/{itemId}")
    public ResponseEntity<Order> addItemToOrder(@PathVariable Integer orderId,@PathVariable Integer itemId){
        Order order = orderService.addItemToOrder(orderId, itemId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>>  ordersHistory(){
        List<Order> orders = orderService.ordersHistory();
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

//    @DeleteMapping("/{orderId}/{itemId}")
//    public ResponseEntity<Order> deleteItemFromOrder(@PathVariable Integer orderId,@PathVariable Integer itemId){
//        Order order = orderService.removeItemFromOrder(orderId, itemId);
//        return new ResponseEntity<>(order,HttpStatus.OK);
//    }
}
