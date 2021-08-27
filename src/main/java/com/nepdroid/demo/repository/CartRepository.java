package com.nepdroid.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nepdroid.demo.model.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	Cart findByCustomerId(Long customerId);

	
}
