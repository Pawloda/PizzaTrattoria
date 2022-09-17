package com.pawlowski.pizzatrattoria.service;

import com.pawlowski.pizzatrattoria.dto.PizzaDTO;

import java.util.List;

public interface PizzaService {

    List<PizzaDTO> readAll();

    PizzaDTO create(PizzaDTO pizzaDTO);

    PizzaDTO readById(Long id);

    PizzaDTO update(PizzaDTO pizzaDTO);

    void deleteById(Long id);

    void deleteIngredientsWithoutPizza();

}
