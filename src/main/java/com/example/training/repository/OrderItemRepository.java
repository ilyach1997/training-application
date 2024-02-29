package com.example.training.repository;

import com.example.training.model.jpa.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

}
