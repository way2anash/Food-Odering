package com.nepdroid.demo.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date orderDate;
	
	@ElementCollection
	private Map<Food,Integer> foodItem;
	private Long customerId;
	private Long paymentId;	
	private double totalPrice;
	
	public Order() {
	
	}
	
	public Order( Date orderDate, Map<Food,Integer> foodItem, Long customerId, Long paymentId, double totalPrice) {
		this.orderDate = orderDate;
		this.foodItem = foodItem;
		this.customerId = customerId;
		this.paymentId = paymentId;
		this.totalPrice = totalPrice;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Map<Food, Integer> getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(Map<Food, Integer> foodItem) {
		this.foodItem = foodItem;
	}

	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderDate=" + orderDate + ", foodItem=" + foodItem + ", customerId=" + customerId
				+ ", paymentId=" + paymentId + ", totalPrice=" + totalPrice + "]";
	}
	
	
}
