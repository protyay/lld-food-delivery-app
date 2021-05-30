package com.lld.orderingapp.controller;

import com.lld.orderingapp.domain.Restaurant;
import com.lld.orderingapp.dto.inbound.AddFoodMenuRequestDTO;
import com.lld.orderingapp.dto.inbound.RegisterRestaurantDTO;
import com.lld.orderingapp.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<String> registerRestaurant(@RequestBody RegisterRestaurantDTO restaurant) {
        if (restaurant == null || restaurant.getAddress() == null || restaurant.getName() == null)
            throw new IllegalArgumentException("Missing parameters");
        String createdId = this.restaurantService.registerRestaurant(restaurant);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    //
    @GetMapping("/search/{name}")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@PathVariable String name) {
        if (name == null) throw new IllegalArgumentException("Invalid search parameters");
        List<Restaurant> response = this.restaurantService.findRestaurantByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/menu")
    public ResponseEntity<String> addFoodMenu(@RequestBody AddFoodMenuRequestDTO foodMenuRequestDTO) {
        if (foodMenuRequestDTO == null) throw new IllegalArgumentException("Request body cannot be null");
        // Fetch the restaurant
        String response = this.restaurantService.addFoodMenu(foodMenuRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Optional<Restaurant>> searchRestaurantById(@PathVariable String id) {
        if (id == null) throw new IllegalArgumentException("Invalid search parameters");
        Optional<Restaurant> response = this.restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
