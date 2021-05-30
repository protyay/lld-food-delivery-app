package com.lld.orderingapp.controller;

import com.lld.orderingapp.domain.Bill;
import com.lld.orderingapp.domain.FoodItem;
import com.lld.orderingapp.domain.User;
import com.lld.orderingapp.domain.UserOrder;
import com.lld.orderingapp.dto.inbound.OrderDetails;
import com.lld.orderingapp.dto.inbound.UserDetailsDTO;
import com.lld.orderingapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/cart/{userId}")
    public ResponseEntity<String> updateCart(@RequestBody List<FoodItem> foodItemList, @PathVariable String userId) {
        if (foodItemList == null || userId == null) throw new IllegalArgumentException("Invalid argument");

        String response = this.userService.updateUserCart(userId, foodItemList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/order/{userId}")
    public ResponseEntity<String> placeOrder(@PathVariable String userId, @RequestBody OrderDetails orderDetails) {
        if (userId == null || orderDetails == null) throw new IllegalArgumentException("Invalid request for order");

        String paymentId = this.userService.placeOrder(userId, orderDetails);
        return new ResponseEntity<>(paymentId, HttpStatus.OK);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<Set<UserOrder>> fetchAllOrders(@PathVariable String userId) {
        if (userId == null) throw new IllegalArgumentException("UserId cannot be NULL");
        Set<UserOrder> orders = this.userService.fetchAllOrders(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        if (userDetailsDTO == null) throw new IllegalArgumentException("Request body cannot be NULL");
        String userId = this.userService.createUser(userDetailsDTO);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> searchUserById(@PathVariable String userId) {
        if (userId == null) throw new IllegalArgumentException("User id cannot be null");

        User response = this.userService.findUserById(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Bill> getBillByOrderId(String orderId, String userId) {
        if (orderId == null || userId == null) throw new IllegalArgumentException("Order ID cannot be NULL");
        Bill orderBill = this.userService.getBillByOrderId(orderId, userId);
        return new ResponseEntity<>(orderBill, HttpStatus.OK);
    }
}
