package com.pawlowski.pizzatrattoria.service;

import com.pawlowski.pizzatrattoria.dto.IngredientDTO;

import java.util.List;

public interface IngredientService {

    List<IngredientDTO> readAll();

    IngredientDTO create(IngredientDTO ingredientDTO);

    IngredientDTO readById(Long id);

    IngredientDTO update(IngredientDTO ingredientDTO);

    void deleteById(Long id);

}
