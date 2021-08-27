package com.nepdroid.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nepdroid.demo.model.Order;
import com.nepdroid.demo.model.OrderInfo;
import com.nepdroid.demo.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/order") 
	public String getOrders() {
		
		return "order";
	}
	
	@PostMapping("/order") 
	public String createOrder(@ModelAttribute("orderInfo") OrderInfo orderInfo, Model model, RedirectAttributes redirectAttributes) {
		
		Order createdOrder = orderService.createOrder(orderInfo.getCartId());
		if(createdOrder.getId() == null) {
			redirectAttributes.addFlashAttribute("message", "Insufficient amount in wallet, couldn't place your order...");
		} 
		else {
			redirectAttributes.addFlashAttribute("message", "Your order is placed sucessfully with order id : "+createdOrder.getId()+" . It will be delivered within an hour.");
		}
		System.out.println("Created Order : "+ createdOrder);
		return "redirect:food";
	}
}
