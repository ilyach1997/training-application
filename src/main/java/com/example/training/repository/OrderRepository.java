package com.example.training.repository;

import com.example.training.model.jpa.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
