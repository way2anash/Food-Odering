package com.nepdroid.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nepdroid.demo.model.Cart;
import com.nepdroid.demo.model.Customer;
import com.nepdroid.demo.model.Food;
import com.nepdroid.demo.model.Order;
import com.nepdroid.demo.model.Payment;
import com.nepdroid.demo.repository.CustomerRepository;
import com.nepdroid.demo.repository.OrderRepository;
import com.nepdroid.demo.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartService cartService;

	public List<Payment> getPayment() {

		return paymentRepository.findAll();
	}

	public Payment createPaymentWithCart(Cart cart) {

		Optional<Customer> customer = customerRepository.findById(cart.getCustomerId());

		if (customer.isPresent()) {

			double walletAmount = customer.get().getWalletAmount();
			int deliveryCharge = 50;
			double totalPrice = cart.getTotalPrice() + deliveryCharge;

			if (totalPrice <= walletAmount) {
				walletAmount -= totalPrice;

				customer.get().setWalletAmount(walletAmount);
				// save customer obj to db........
				customerRepository.save(customer.get());

				Payment payment = new Payment(new Date(), totalPrice);
				Payment createdPayment = paymentRepository.save(payment);

				HashMap<Food, Integer> foodItem = new HashMap<Food, Integer>();
				// Creating Map
				for (Map.Entry<Food, Integer> entry : cart.getCartItem().entrySet()) {
					int quantity = entry.getValue();
					Food food = entry.getKey();
					foodItem.put(food, quantity);
				}

				// Creating order after payment
				Order order = new Order(new Date(), foodItem, cart.getCustomerId(), createdPayment.getId(), totalPrice);
				orderRepository.save(order);
				System.out.println("Order : " + order);
				System.out.println("Payment : " + payment);

				// resetting user's cart after order is placed
				cartService.resetCartItem(order.getCustomerId());

				return createdPayment;
			} else {
				System.out.println("Insufficient amount in wallet, couldn't place order...");
			}

		}

		return new Payment();
	}
}
