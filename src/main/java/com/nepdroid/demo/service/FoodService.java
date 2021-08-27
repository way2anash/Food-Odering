package com.nepdroid.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nepdroid.demo.model.Food;
import com.nepdroid.demo.repository.FoodRepository;

@Service
public class FoodService {

	@Autowired
	private FoodRepository foodRepository;
	
	public List<Food> getFood() {
		
		return foodRepository.findAll();
	}
	
	public Optional<Food> getFoodById(Long foodId) {
		
		return foodRepository.findById(foodId);
	}
	
	public List<Food> addFood (List<Food> food) {
		
		return foodRepository.saveAll(food);
	}

}
