package com.lld.orderingapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Payment {

    private String paymentId;
    private String orderId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private Bill billReference;
}
