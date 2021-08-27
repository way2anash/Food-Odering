package com.nepdroid.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nepdroid.demo.model.Customer;
import com.nepdroid.demo.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> getCustomer() {
		
		return customerRepository.findAll();
	}
	
	public Customer addCustomer (Customer customer) {
		
		return customerRepository.save(customer);
	}
	
	public Customer findCusotomerByEmail (String email) {
		
		return customerRepository.findByEmail(email);
	}
	
	public List<Customer> addAllCustomer (List<Customer> customer) {
		
		return customerRepository.saveAll(customer);
	}

	public Optional<Customer> getCustomerById(Long customerId) {
		
		return customerRepository.findById(customerId);
	}
}
