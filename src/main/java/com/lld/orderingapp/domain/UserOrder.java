package com.lld.orderingapp.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserOrder {
    private String orderId;
    private Double orderAmount;
    private List<FoodItem> foodItemList;
    private LocalDate orderDate;
    private DeliveryAddress orderDeliveryAddress;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private CouponCode couponCode;
    private String restaurantId;
    private Bill orderBill;
}
