package com.nepdroid.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nepdroid.demo.model.Cart;
import com.nepdroid.demo.model.Customer;
import com.nepdroid.demo.model.Food;
import com.nepdroid.demo.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FoodService foodService;

	public List<Cart> getCart() {

		return cartRepository.findAll();
	}

	public Optional<Cart> findCartById(Long id) {

		return cartRepository.findById(id);
	}

	public Cart findCartByCustomerId(Long customerId) {

		return cartRepository.findByCustomerId(customerId);
	}

	public Cart addCart(Food food) {
		String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerService.findCusotomerByEmail(customerEmail);

		System.out.println("Inside addCart method.......");
		System.out.println("Food : " + food);
		Cart cart = findCartByCustomerId(customer.getId());
		// if Cart doesn't exist then create new one otherwise update
		if (cart == null) {
			System.out.println("Cart for this user couldn't found,Going to Create Cart..");
			Map<Food, Integer> cartItem = new HashMap<Food, Integer>();
			cartItem.put(food, 1);
			double totalPrice = calculateTotalPrice(cartItem);

			Cart newCart = new Cart(customer.getId(), cartItem, totalPrice);
			return cartRepository.save(newCart);
		} else {
			System.out.println("Cart Found, Going to update Cart with new additional food item..");
			return updateCartItem(cart, food);
		}

	}

	public Cart updateCartItem(Cart cart, Food food) {
		boolean isFoodNew = true;

		Map<Food, Integer> updatedCartItem = new HashMap<Food, Integer>();
		Map<Food, Integer> foodItem = cart.getCartItem();
		for (Map.Entry<Food, Integer> entry : foodItem.entrySet()) {

			if (entry.getKey().getId() == food.getId()) {
				isFoodNew = false;
				System.out.println("Match Found for Food Id : " + food.getId());
				int quantity = entry.getValue();
				quantity++;

				updatedCartItem.put(entry.getKey(), quantity);
				System.out.println("Key : " + entry.getKey() + " , Quantity : " + quantity);
			} else {
				updatedCartItem.put(entry.getKey(), entry.getValue());
				System.out.println("Key : " + entry.getKey() + " , Quantity : " + entry.getValue());
			}
		}

		if (isFoodNew == true) {
			System.out.println("This Food item is new to cart so adding it...");
			updatedCartItem.put(food, 1);
		}

		double totalPrice = calculateTotalPrice(updatedCartItem);
		cart.setCartItem(updatedCartItem);
		cart.setTotalPrice(totalPrice);
		System.out.println("New Cart Data : " + cart);
		return cartRepository.save(cart);

	}

	public double calculateTotalPrice(Map<Food, Integer> updatedCartItem) {
		double totalPrice = 0;
		for (Map.Entry<Food, Integer> entry : updatedCartItem.entrySet()) {
			int quantity = entry.getValue();
			Food food = entry.getKey();
			float price = food.getPrice();
			totalPrice += (quantity * price);
		}

		return totalPrice;
	}

	// removing single whole food object from the cart
	public Cart removeCartItem(Food food) {
		// put customer by auth
		String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerService.findCusotomerByEmail(customerEmail);
		Cart cart = cartRepository.findByCustomerId(customer.getId());
		Optional<Food> removeFood = foodService.getFoodById(food.getId());
		System.out.println("Cart Before removal of food : " + cart);
		double totalPrice = 0;
		Map<Food, Integer> entry = cart.getCartItem();
		entry.remove(removeFood.get());
		totalPrice = calculateTotalPrice(entry);
		cart.setCartItem(entry);
		cart.setTotalPrice(totalPrice);

		return cartRepository.save(cart);
	}

	public void resetCartItem(Long customerId) {

		Cart cart = cartRepository.findByCustomerId(customerId);
		System.out.println("Cart Before removal of food : " + cart);
		Map<Food, Integer> entry = cart.getCartItem();
		entry.clear();
		cart.setCartItem(entry);
		cart.setTotalPrice(0.0);
		cartRepository.save(cart);
	}
}
