package com.nepdroid.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nepdroid.demo.model.Cart;
import com.nepdroid.demo.model.Customer;
import com.nepdroid.demo.model.Food;
import com.nepdroid.demo.model.OrderInfo;
import com.nepdroid.demo.service.CartService;
import com.nepdroid.demo.service.CustomerService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private CustomerService customerService;

	
	@GetMapping("/cart") 
	public String getCart(Model model) {
		
		String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Customer customer = customerService.findCusotomerByEmail(customerEmail);
		System.out.println("Current User email : "+ customerEmail);
		Cart cart = cartService.findCartByCustomerId(customer.getId());
		if(cart.getCartItem().isEmpty()) {
			return "emptycart";
		}
		model.addAttribute("cart", cart);
		model.addAttribute("deliveryCharge", 50);
		model.addAttribute("cartFood", new Food());
		model.addAttribute("orderInfo", new OrderInfo());
		return "cart";
	}
	
	@PostMapping("/removeCartItem")
	public String removeCartItem(@ModelAttribute("cartFood") Food food, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("Going to remove food : "+ food);
		Cart cart = cartService.removeCartItem( food);
		System.out.println("Cart after removal of food : "+cart);
		model.addAttribute("cart", cart);
		model.addAttribute("cartFood", new Food());
		return "redirect:cart";
	}
	
	@PostMapping("/cart")
	public String createCart(@ModelAttribute("cartFood") Food food, Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", " Item successfully added to your cart.");
		Cart cart = cartService.addCart(food);
		System.out.println("New Cart : "+ cart);
		return "redirect:food";
	}
}
