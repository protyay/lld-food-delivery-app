package com.lld.orderingapp.domain;

public enum CouponCode {
    FIFTY_OFF(5),
    TEN_CASHBACK(10);

    private final int discountPercent;

    CouponCode(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getDiscount() {
        return this.discountPercent;
    }
}
