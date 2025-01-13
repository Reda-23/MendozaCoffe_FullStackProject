package org.mendozaproject.services;

import org.mendozaproject.models.Bill;
import org.mendozaproject.models.Customer;
import org.mendozaproject.models.Order;
import org.mendozaproject.enums.PaymentMethod;

import java.util.List;

public interface BillService {



    Bill generateBill( Order order , PaymentMethod paymentMethod , double tax );


    Bill createBill(Integer orderId, int paymentMethod , int tax);


    List<Bill> billsHistory();
}
