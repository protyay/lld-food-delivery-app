package com.lld.orderingapp.domain;

import lombok.Data;

import java.util.List;

@Data
public class UserCart {
    private String userCartId;
    private List<FoodItem> foodItems;
}
