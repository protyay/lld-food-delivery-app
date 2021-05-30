package com.lld.orderingapp.service;

import com.lld.orderingapp.domain.Restaurant;
import com.lld.orderingapp.dto.inbound.AddFoodMenuRequestDTO;
import com.lld.orderingapp.dto.inbound.RegisterRestaurantDTO;
import com.lld.orderingapp.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public String registerRestaurant(RegisterRestaurantDTO registerRestaurantDTO) {
        String restaurantId = UUID.randomUUID().toString();
        Restaurant restaurant = new Restaurant();
        restaurant.setName(registerRestaurantDTO.getName());
        restaurant.setAddress(registerRestaurantDTO.getAddress());
        restaurant.setRestaurantId(restaurantId);
        restaurant.setTaxSlab(registerRestaurantDTO.getTaxSlab());

        return this.restaurantRepository.saveRestaurant(restaurant);
    }

    public List<Restaurant> findRestaurantByName(String name) {
        return this.restaurantRepository.findAllByName(name);
    }

    public String addFoodMenu(AddFoodMenuRequestDTO foodMenuRequestDTO) {
        Restaurant restaurant = this.restaurantRepository.getRestaurantById(foodMenuRequestDTO.getRestaurantId());
        restaurant.setFoodMenu(foodMenuRequestDTO.getFoodMenu());
        return restaurant.getRestaurantId();
    }

    public Optional<Restaurant> findRestaurantById(String restaurantId) {
        return this.restaurantRepository.findById(restaurantId);
    }
}
