package com.lld.orderingapp.domain;

import lombok.Data;

@Data
public class Address {
    private String addressId;
    private String country;
    private String city;
    private String state;
    private String zipCode;
    private String addLine1;
    private String addLine2;
}
