package org.mendozaproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mendozaproject.enums.PaymentMethod;

@Entity
@Table(name = "BILLS")
public class Bill {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billId;
    @OneToOne
    private Order order;
    private double totalPrice;
    private double tax;
    private double finalPrice;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public Bill(Integer billId, Order order, double totalPrice, double tax, double finalPrice, PaymentMethod paymentMethod) {
        this.billId = billId;
        this.order = order;
        this.totalPrice = totalPrice;
        this.tax = tax;
        this.finalPrice = finalPrice;
        this.paymentMethod = paymentMethod;
    }

    public Bill() {
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
