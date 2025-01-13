package org.mendozaproject.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.mendozaproject.models.Bill;
import org.mendozaproject.models.Customer;
import org.mendozaproject.models.Order;
import org.mendozaproject.enums.PaymentMethod;
import org.mendozaproject.repos.BillRepository;
import org.mendozaproject.repos.CustomerRepository;
import org.mendozaproject.repos.OrderRepository;
import org.mendozaproject.services.BillService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class BillServiceImpl implements BillService {


    private BillRepository billRepository;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;

    public BillServiceImpl(BillRepository billRepository, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }


    // Not Used Method Ready To Be Deleted
    @Override
    public Bill generateBill( Order order, PaymentMethod paymentMethod, double tax) {
        Bill bill = new Bill();
        bill.setTax(tax);
        bill.setOrder(order);
        bill.setTotalPrice(order.getOrderPrice());
        double finalPrc = order.getOrderPrice() + (order.getOrderPrice() * tax);
        bill.setFinalPrice(finalPrc);
        bill.setPaymentMethod(paymentMethod);
        return billRepository.save(bill);
    }

    @Override
    public Bill createBill(Integer orderId, int paymentMethod, int tax) {
        Order order = orderRepository.findOrderByOrderId(orderId);
        if (order == null) throw new RuntimeException("Order is not found : " + orderId);
        Bill bill = new Bill();
        bill.setOrder(order);
        bill.setTotalPrice(order.getOrderPrice());
        bill.setPaymentMethod(getMethodPayment(paymentMethod));
        bill.setTax(tax);
        double tx =  ((double) tax / 100);
        bill.setFinalPrice(order.getOrderPrice() + (order.getOrderPrice() * tx));
        var savedBill = billRepository.save(bill);
        return savedBill;
    }

    @Override
    public List<Bill> billsHistory(){
        List<Bill> bills = billRepository.findAll();
        return bills;
    }




    private PaymentMethod getMethodPayment(int pm){
       return switch (pm){
            case 1 -> PaymentMethod.CASH;
            case 2 -> PaymentMethod.CREDIT_CARD;
            case 3 -> PaymentMethod.MOBILE_APPLICATION;
           default -> throw new IllegalStateException("Unexpected value: " + pm);
       };
    }
}
