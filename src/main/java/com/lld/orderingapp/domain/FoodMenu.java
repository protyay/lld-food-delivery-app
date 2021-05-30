package com.lld.orderingapp.domain;

import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class FoodMenu {

    private int menuId;
    private Set<Dish> dishItems;
    private Instant updatedAt;
    private String updatedBy;
}
