package com.lld.orderingapp.repository;

import com.lld.orderingapp.domain.Restaurant;
import com.lld.orderingapp.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RestaurantRepository {


    private final Map<String, Restaurant> restaurantMap;

    public RestaurantRepository(Map<String, Restaurant> restaurantMap) {
        this.restaurantMap = new HashMap<>();
    }

    public String saveRestaurant(Restaurant restaurant) {
        this.restaurantMap.put(restaurant.getRestaurantId(), restaurant);
        return restaurant.getRestaurantId();
    }

    public Restaurant getRestaurantById(String restaurantId) {
        if (!this.restaurantMap.containsKey(restaurantId))
            throw new ResourceNotFoundException("No restaurant exists with id" + restaurantId);
        return this.restaurantMap.get(restaurantId);
    }

    public List<Restaurant> findAllByName(String name) {
        final List<Restaurant> restaurantList = this.restaurantMap.values().stream().takeWhile(obj -> obj.getName().equals(name)).collect(Collectors.toList());
        return restaurantList;
    }

    public Optional<Restaurant> findById(String restaurantId) {
        if (!this.restaurantMap.containsKey(restaurantId)) return Optional.empty();

        return Optional.of(this.restaurantMap.get(restaurantId));
    }
}
