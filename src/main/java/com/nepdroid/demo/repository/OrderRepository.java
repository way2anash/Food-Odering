package com.nepdroid.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nepdroid.demo.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	Optional<Order> findByPaymentId(Long id);

	
}
