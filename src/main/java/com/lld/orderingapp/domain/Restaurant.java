package com.lld.orderingapp.domain;

import lombok.Data;

import java.util.NavigableMap;

@Data
public class Restaurant {
    private String restaurantId;
    private String name;
    private FoodMenu foodMenu;
    private Address address;
    private NavigableMap<Integer, Double> taxSlab;
}
