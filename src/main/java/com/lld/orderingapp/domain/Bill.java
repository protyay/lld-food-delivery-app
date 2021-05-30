package com.lld.orderingapp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Bill {
    private Double totalAmount;
    private Double taxAmount;
    private Double discountValue;
    private Double finalPrice;
}
