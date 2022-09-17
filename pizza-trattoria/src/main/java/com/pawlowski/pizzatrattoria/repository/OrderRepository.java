package com.pawlowski.pizzatrattoria.repository;

import com.pawlowski.pizzatrattoria.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

}
