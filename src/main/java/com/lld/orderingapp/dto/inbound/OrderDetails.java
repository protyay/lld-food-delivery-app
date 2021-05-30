package com.lld.orderingapp.dto.inbound;

import com.lld.orderingapp.domain.CouponCode;
import com.lld.orderingapp.domain.DeliveryAddress;
import com.lld.orderingapp.domain.FoodItem;
import com.lld.orderingapp.domain.PaymentMethod;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.List;

@Getter
@Setter
public class OrderDetails {
    private List<FoodItem> foodItemList;
    private CouponCode couponCode;
    @NonNull
    private PaymentMethod paymentMethod;
    @NonNull
    private DeliveryAddress orderDeliveryAddress;
    @NonNull
    private String restaurantId;
}
