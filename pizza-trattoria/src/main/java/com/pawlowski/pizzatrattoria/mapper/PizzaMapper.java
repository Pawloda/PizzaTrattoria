package com.pawlowski.pizzatrattoria.mapper;

import com.pawlowski.pizzatrattoria.dto.PizzaDTO;
import com.pawlowski.pizzatrattoria.entity.IngredientEntity;
import com.pawlowski.pizzatrattoria.entity.PizzaEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class PizzaMapper {

    private PizzaMapper() {
        //Class for utils only
    }

    public static List<PizzaDTO> mapToDTOs(List<PizzaEntity> pizzaEntities) {
        List<PizzaDTO> pizzaDTOS = new ArrayList<>(pizzaEntities.size());
        for(PizzaEntity pizzaEntity : pizzaEntities) {
            pizzaDTOS.add(mapToDTO(pizzaEntity));
        }
        return pizzaDTOS;
    }

    public static PizzaDTO mapToDTO(PizzaEntity pizzaEntity) {
        List<IngredientEntity> ingredients = pizzaEntity.getIngredients();
        return PizzaDTO.builder()
                .ingredients(IngredientMapper.mapToDTOs(ingredients))
                .id(pizzaEntity.getId())
                .price(pizzaEntity.getPrice())
                .name(pizzaEntity.getName())
                .stringIngredients(createStrings(ingredients))
                .build();
    }

    public static List<PizzaEntity> mapToEntities(List<PizzaDTO> pizzaDTOS) {
        List<PizzaEntity> pizzaEntities = new ArrayList<>();
        if(nonNull(pizzaDTOS) && !pizzaDTOS.isEmpty()) {
            for(PizzaDTO pizzaDTO : pizzaDTOS) {
                pizzaEntities.add(mapToEntity(pizzaDTO));
            }
        }
        return pizzaEntities;
    }

    public static PizzaEntity mapToEntity(PizzaDTO pizzaDTO) {
        return PizzaEntity.builder()
                .ingredients(IngredientMapper.mapToEntities(pizzaDTO.getIngredients()))
                .id(pizzaDTO.getId())
                .price(pizzaDTO.getPrice())
                .name(pizzaDTO.getName())
                .stringIngredients(pizzaDTO.getStringIngredients())
                .build();
    }

    private static List<String> createStrings(List<IngredientEntity> ingredients) {
        List<String> stringIngredients = new ArrayList<>();
        for(IngredientEntity ingredientEntity : ingredients) {
            stringIngredients.add(ingredientEntity.getName());
        }
        return stringIngredients;
    }

}
