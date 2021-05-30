package com.lld.orderingapp;

import com.lld.orderingapp.controller.RestaurantController;
import com.lld.orderingapp.controller.UserController;
import com.lld.orderingapp.domain.*;
import com.lld.orderingapp.dto.inbound.OrderDetails;
import com.lld.orderingapp.dto.inbound.RegisterRestaurantDTO;
import com.lld.orderingapp.dto.inbound.UserDetailsDTO;
import com.lld.orderingapp.exception.ResourceNotFoundException;
import com.lld.orderingapp.repository.PaymentRepository;
import com.lld.orderingapp.repository.RestaurantRepository;
import com.lld.orderingapp.repository.UserRepository;
import com.lld.orderingapp.service.BillGenerationService;
import com.lld.orderingapp.service.PaymentService;
import com.lld.orderingapp.service.RestaurantService;
import com.lld.orderingapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FoodOrderingAppApplicationTests {
    private RestaurantController restaurantController;
    private UserController userController;
    private RegisterRestaurantDTO dto;
    private UserDetailsDTO userDetailsDTO;

    @Test
    void contextLoads() {

    }

    @BeforeEach
    public void setup() {
        // Create instances
        RestaurantRepository restaurantRepository = new RestaurantRepository(new HashMap<>());
        RestaurantService restaurantService = new RestaurantService(restaurantRepository);
        restaurantController = new RestaurantController(restaurantService);
        dto = new RegisterRestaurantDTO();
        final UserRepository userRepository = new UserRepository();

        final PaymentRepository paymentRepository = new PaymentRepository();
        final BillGenerationService billGenerationService = new BillGenerationService(restaurantService);
        final PaymentService paymentService = new PaymentService(paymentRepository);
        final UserService userService = new UserService(userRepository, paymentService, billGenerationService);

        userController = new UserController(userService);

        // Test registration of restaurant
        Address restaurantAdd = new Address();
        restaurantAdd.setAddLine1("ADDl 1");
        restaurantAdd.setCity("Kolkata");
        restaurantAdd.setState("WB");
        restaurantAdd.setZipCode("712235");

        dto.setAddress(restaurantAdd);

        dto.setName("Chowman");
        NavigableMap<Integer, Double> taxMap = new TreeMap<>();
        taxMap.put(1000, 5.5);
        taxMap.put(2000, 10.5);
        dto.setTaxSlab(taxMap);


        userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setPhoneNum("9903399296");
        userDetailsDTO.setUsername("protyayb");

    }

    @Test
    public void test_addAndFetchRestaurantSuccessfully() {
        ResponseEntity<String> restaurantCreatedId = restaurantController.registerRestaurant(dto);


        ResponseEntity<List<Restaurant>> searchByName = restaurantController.searchRestaurant("Chowman");
        assertAll(
                () -> assertNotNull(searchByName.getBody()),
                () -> assertEquals(1, searchByName.getBody().size())
        );
    }

    @Test
    public void test_AddUser_FetchUser(){

        final ResponseEntity<String> user = this.userController.createUser(userDetailsDTO);
        assertNotNull(user);
        assertNotNull(user.getBody());

        assertThrows(IllegalArgumentException.class, () -> this.userController.searchUserById(null));
        assertThrows(ResourceNotFoundException.class, () -> this.userController.searchUserById("123"));

        ResponseEntity<User> searchUser = this.userController.searchUserById(user.getBody());

        assertNotNull(searchUser);
        assertNotNull(searchUser.getBody());
        assertEquals("protyayb", searchUser.getBody().getName());

    }

    @Test
    public void test_placeOrderSuccessfully_andFetchOrder() {
        final ResponseEntity<String> restaurantCreatedId = restaurantController.registerRestaurant(dto);
        final ResponseEntity<String> user = this.userController.createUser(userDetailsDTO);

        // When the user places an order, we would be able to successfully persist the order
        // User can query the order and view the bill for related order

        // Given, Build order details
        OrderDetails orderDetails = new OrderDetails();
        List<FoodItem> foodItems = TestHelper.buildFoodItems(5);

        orderDetails.setFoodItemList(foodItems);
        orderDetails.setCouponCode(CouponCode.FIFTY_OFF);
        orderDetails.setPaymentMethod(PaymentMethod.DEBIT_CARD);
        orderDetails.setRestaurantId(Objects.requireNonNull(restaurantCreatedId.getBody()));

        final ResponseEntity<String> orderId = this.userController.placeOrder(user.getBody(), orderDetails);
        assertTrue(orderId != null && orderId.getBody() != null);

        // We would query the bill by the order ID
        final ResponseEntity<Bill> orderBill = this.userController.getBillByOrderId(orderId.getBody(), user.getBody());
        assertNotNull(orderBill);
        assertNotNull(orderBill.getBody());
        Bill bill = orderBill.getBody();

        assertEquals(3000.00, bill.getTotalAmount());

    }
}

class TestHelper {
    public static List<FoodItem> buildFoodItems(int count) {
        List<FoodItem> foodItems = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            FoodItem foodItem = new FoodItem();
            foodItem.setDish(Dish.builder().dishId("" + i).dishPrice(200)
                            .dishName("Chilli Chicken"+i).category(MealType.LUNCH)
                            .cuisineType(CuisineType.CHINESE_CUISINE)
                            .build());
            foodItem.setQuantity(3);

            foodItems.add(foodItem);
        }
        return foodItems;
    }


}
