package com.pawlowski.pizzatrattoria.repository;

import com.pawlowski.pizzatrattoria.entity.PizzaEntity;
import org.springframework.data.repository.CrudRepository;

public interface PizzaRepository extends CrudRepository<PizzaEntity, Long> {

}
