package com.lld.orderingapp.service;

import com.lld.orderingapp.domain.Bill;
import com.lld.orderingapp.domain.FoodItem;
import com.lld.orderingapp.domain.User;
import com.lld.orderingapp.domain.UserOrder;
import com.lld.orderingapp.dto.inbound.OrderDetails;
import com.lld.orderingapp.dto.inbound.UserDetailsDTO;
import com.lld.orderingapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final BillGenerationService billGenerationService;

    public UserService(UserRepository userRepository, PaymentService paymentService, BillGenerationService billGenerationService) {
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.billGenerationService = billGenerationService;
    }

    public User findUserById(String userId) {
        return this.userRepository.findByUserId(userId);
    }

    public String updateUserCart(final String userId, final List<FoodItem> foodItemList) {
        return this.userRepository.updateCart(userId, foodItemList);
    }

    public String placeOrder(String userId, final OrderDetails orderDetails) {

        UserOrder userOrder = new UserOrder();

        Bill orderBill = this.billGenerationService.generateBill(orderDetails.getFoodItemList(),
                List.of(orderDetails.getCouponCode()), orderDetails.getRestaurantId());

        userOrder.setOrderId(UUID.randomUUID().toString());
        userOrder.setFoodItemList(orderDetails.getFoodItemList());
        userOrder.setPaymentMethod(orderDetails.getPaymentMethod());
        userOrder.setOrderAmount(orderBill.getTotalAmount());
        userOrder.setOrderDeliveryAddress(orderDetails.getOrderDeliveryAddress());
        userOrder.setCouponCode(orderDetails.getCouponCode());
        userOrder.setOrderBill(orderBill);

        String orderId = this.userRepository.placeOrder(userId, userOrder);

        this.paymentService.processOrderPayment(userOrder, userId, orderBill);
        return orderId;
    }

    public Set<UserOrder> fetchAllOrders(String userId) {
        return this.userRepository.fetchAllOrder(userId);
    }

    public String createUser(UserDetailsDTO userDetailsDTO) {
        final User newUser = User.builder().name(userDetailsDTO.getUsername())
                .phoneNum(userDetailsDTO.getPhoneNum())
                .userId(UUID.randomUUID().toString())
                .registrationDate(LocalDate.now()).build();

        return this.userRepository.saveUser(newUser);
    }

    public Bill getBillByOrderId(String orderId, String userId) {
        return this.userRepository.findBill(orderId, userId);
    }
}
