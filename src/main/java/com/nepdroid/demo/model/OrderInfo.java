package com.nepdroid.demo.model;

public class OrderInfo {

	private Long cartId;

	
	public OrderInfo() {

	}

	
	public OrderInfo(Long cartId) {
		super();
		this.cartId = cartId;
	}


	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	
	
}
