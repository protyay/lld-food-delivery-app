package com.lld.orderingapp.dto.inbound;

import com.lld.orderingapp.domain.Address;
import lombok.Data;

import java.util.NavigableMap;

@Data
public class RegisterRestaurantDTO {
    private String name;
    private Address address;
    private NavigableMap<Integer, Double> taxSlab;
}
