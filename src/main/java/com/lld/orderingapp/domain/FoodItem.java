package com.lld.orderingapp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FoodItem {
    private Dish dish;
    private int quantity;

    public double getDishPrice() {
        return this.quantity * this.getDish().getDishPrice();
    }
}
