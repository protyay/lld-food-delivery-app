package com.lld.orderingapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Dish {
    private String dishId;
    @NonNull
    private String dishName;
    @NonNull
    private int dishPrice;
    @NonNull
    private MealType category;
    @NonNull
    private CuisineType cuisineType;
}
