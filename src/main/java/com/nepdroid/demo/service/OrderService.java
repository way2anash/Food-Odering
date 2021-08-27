package com.nepdroid.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nepdroid.demo.model.Cart;
import com.nepdroid.demo.model.Order;
import com.nepdroid.demo.model.Payment;
import com.nepdroid.demo.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired 
	private CartService cartService;
	
	@Autowired 
	private PaymentService paymentService;
	
	public List<Order> getOrder() {
		
		return orderRepository.findAll();
	}
	
	public Optional<Order> getOrderById( Long id) {
		
		return orderRepository.findById(id);
	}
	
	public Optional<Order> getOrderByPaymentId( Long id) {
		
		return orderRepository.findByPaymentId(id);
	}
	
	public Order createOrder( Long cartId) {
		
		Optional<Cart> cart = cartService.findCartById(cartId);
        if(cart.isPresent()) {
    		Payment payment = paymentService.createPaymentWithCart(cart.get());
    		
    		if(payment != null) {
    			
    			Optional<Order> retrievedOrder = getOrderByPaymentId(payment.getId());
    			if(retrievedOrder.isPresent()) {
    				return retrievedOrder.get();
    			}
    		}
        }
		
		return new Order();
	}
	
	
}
