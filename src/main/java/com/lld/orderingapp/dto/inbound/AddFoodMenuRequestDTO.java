package com.lld.orderingapp.dto.inbound;

import com.lld.orderingapp.domain.FoodMenu;
import lombok.Data;

@Data
public class AddFoodMenuRequestDTO {
    private final String restaurantId;
    private final FoodMenu foodMenu;
}
