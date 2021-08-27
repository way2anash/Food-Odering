package com.nepdroid.demo.model;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cart {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long customerId;
	
	@ElementCollection
	//consists of a Map of <productId, quantity> of Food Item added to Cart.
	private Map<Food,Integer> cartItem;
	private double totalPrice;
	
	public Cart() {

	}
	

	public Cart(Long customerId, Map<Food, Integer> cartItem, double totalPrice) {
		super();
		this.customerId = customerId;
		this.cartItem = cartItem;
		this.totalPrice = totalPrice;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public Map<Food, Integer> getCartItem() {
		return cartItem;
	}

	public void setCartItem(Map<Food, Integer> cartItem) {
		this.cartItem = cartItem;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", customerId=" + customerId + ", cartItem=" + cartItem + ", totalPrice=" + totalPrice
				+ "]";
	}
	
}
