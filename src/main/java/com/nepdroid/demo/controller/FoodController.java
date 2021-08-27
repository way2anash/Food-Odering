package com.nepdroid.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nepdroid.demo.model.Food;
import com.nepdroid.demo.service.FoodService;

@Controller
public class FoodController {
	
	@Autowired
	private FoodService foodService;


	@GetMapping("/")
	public String showDashboard(Model model) {
		List<Food> food = foodService.getFood();
		model.addAttribute("cartFood", new Food());
		model.addAttribute("foods", food);
		return "index";
	}
	
	@GetMapping("/food")
	public String getFoods(Model model) {
		List<Food> food = foodService.getFood();
		model.addAttribute("cartFood", new Food());
		model.addAttribute("foods", food);
		return "index";
	}
	
	

}
