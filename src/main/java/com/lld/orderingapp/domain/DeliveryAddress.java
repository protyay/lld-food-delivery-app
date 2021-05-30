package com.lld.orderingapp.domain;

import lombok.Data;

import java.util.List;

@Data
public class DeliveryAddress extends Address {

    private boolean isPrimaryAddress;
    private List<String> addressTags;
}
