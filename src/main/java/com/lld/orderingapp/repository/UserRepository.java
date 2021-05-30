package com.lld.orderingapp.repository;

import com.lld.orderingapp.domain.*;
import com.lld.orderingapp.exception.DuplicateUserException;
import com.lld.orderingapp.exception.InvalidStateException;
import com.lld.orderingapp.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final Map<String, User> userMap = new HashMap<>();
    private final Map<String, UserCart> userCartMap = new HashMap<>();
    // User order map is MAP of User ID to Order Map. Order MAP is orderID to the corresponding user order
    private final Map<String, Map<String, UserOrder>> userOrderMap = new HashMap<>();

    public User findByUserId(String userId) {
        if (!this.userMap.containsKey(userId))
            throw new ResourceNotFoundException("User not found with ID = " + userId);
        return this.userMap.get(userId);
    }

    public String updateCart(String userId, final List<FoodItem> foodItemList) {
        if (!this.userMap.containsKey(userId))
            throw new ResourceNotFoundException("User not found with userId = " + userId);

        UserCart userCart = new UserCart();
        userCart.setFoodItems(foodItemList);
        userCart.setUserCartId(UUID.randomUUID().toString());

        this.userCartMap.put(userId, userCart);
        return userCart.getUserCartId();
    }

    public String placeOrder(String userId, UserOrder userOrder) {
        if (!this.userMap.containsKey(userId))
            throw new ResourceNotFoundException("User not found with userId = " + userId);
        this.userOrderMap.computeIfAbsent(userId, id -> new HashMap<>());
        this.userOrderMap.get(userId).put(userOrder.getOrderId(), userOrder);
        return userOrder.getOrderId();
    }

    public Set<UserOrder> fetchAllOrder(String userId) {
        if (!this.userMap.containsKey(userId)) throw new ResourceNotFoundException("Invalid user ID");
        Map<String, UserOrder> orders = this.userOrderMap.get(userId);
        Set<UserOrder> allOrders = new HashSet<>(orders.values());
        return allOrders;
    }

    public String saveUser(User newUser) {
        if (userMap.containsKey(newUser.getUserId())) throw new DuplicateUserException("user already exists");
        this.userMap.put(newUser.getUserId(), newUser);
        return newUser.getUserId();
    }

    public Bill findBill(String orderId, String userId) {
        if (!this.userMap.containsKey(userId)) throw new ResourceNotFoundException("Invalid user ID");
        if (!this.userOrderMap.containsKey(userId) || !this.userOrderMap.get(userId).containsKey(orderId))
            throw new ResourceNotFoundException("No such order found for user id");

        UserOrder orderBill = this.userOrderMap.get(userId).get(orderId);
        if (orderBill == null) throw new InvalidStateException("Order cannot be NULL");
        return orderBill.getOrderBill();
    }
}
