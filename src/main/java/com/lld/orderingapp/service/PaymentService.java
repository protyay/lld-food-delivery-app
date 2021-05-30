package com.lld.orderingapp.service;

import com.lld.orderingapp.domain.Bill;
import com.lld.orderingapp.domain.Payment;
import com.lld.orderingapp.domain.PaymentStatus;
import com.lld.orderingapp.domain.UserOrder;
import com.lld.orderingapp.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {


    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public String processOrderPayment(UserOrder userOrder, String userId, Bill orderBill) {

        Payment payment = Payment.builder().paymentId(UUID.randomUUID().toString())
                .paymentMethod(userOrder.getPaymentMethod())
                .billReference(orderBill).paymentStatus(PaymentStatus.INITIATED)
                .build();

        this.paymentRepository.savePayment(payment, userOrder);
        return payment.getPaymentId();
    }
}
