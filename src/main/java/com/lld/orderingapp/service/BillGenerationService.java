package com.lld.orderingapp.service;

import com.lld.orderingapp.domain.Bill;
import com.lld.orderingapp.domain.CouponCode;
import com.lld.orderingapp.domain.FoodItem;
import com.lld.orderingapp.domain.Restaurant;
import com.lld.orderingapp.exception.InvalidRestaurantException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BillGenerationService {

    private final RestaurantService restaurantService;

    public BillGenerationService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * Given a list of food items and
     * a list of coupon codes,we'll generate bill
     * We currently maintain taxSlab for each restaurant which we query to fetch the tax percentage
     * based on the order amount
     *
     * @param foodItemList
     * @param couponCodes
     * @return the Bill for the order
     */
    public Bill generateBill(List<FoodItem> foodItemList, List<CouponCode> couponCodes, String restaurantId) {
        final double totalBillAmount = foodItemList.stream().mapToDouble(FoodItem::getDishPrice).sum();
        Optional<Restaurant> restaurant = this.restaurantService.findRestaurantById(restaurantId);
        if (restaurant.isEmpty()) throw new InvalidRestaurantException("Please provide valid restaurant ID");
        final int totalDisc = couponCodes.stream().mapToInt(CouponCode::getDiscount).sum();

        final Map.Entry<Integer, Double> totalTax = restaurant.get().getTaxSlab().floorEntry((int) Math.ceil(totalBillAmount));
        final double totalTaxAmount = .01 * totalTax.getValue() * totalBillAmount;
        final double withTaxPrice = totalTaxAmount + totalBillAmount;

        final double finalPrice = withTaxPrice * (100 - totalDisc) * 0.01;

        final Bill finalBill = Bill.builder()
                .totalAmount(totalBillAmount)
                .finalPrice(finalPrice)
                .taxAmount(totalTaxAmount)
                .build();

        return finalBill;
    }
}
