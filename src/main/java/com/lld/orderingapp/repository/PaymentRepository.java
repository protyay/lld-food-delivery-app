package com.lld.orderingapp.repository;

import com.lld.orderingapp.domain.Payment;
import com.lld.orderingapp.domain.UserOrder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PaymentRepository {

    private final Map<String, Payment> orderToPaymentMap = new HashMap<>();

    public String savePayment(Payment payment, UserOrder userOrder) {
        this.orderToPaymentMap.put(userOrder.getOrderId(), payment);
        return payment.getPaymentId();
    }
}
