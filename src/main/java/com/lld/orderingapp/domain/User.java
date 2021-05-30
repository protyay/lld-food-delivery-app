package com.lld.orderingapp.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class User {
    private final String userId;
    private final List<DeliveryAddress> deliveryAddressList;
    private final LocalDate registrationDate;
    private final String name;
    private final String phoneNum;

}
