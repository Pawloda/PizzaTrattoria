package com.pawlowski.pizzatrattoria.mapper;

import com.pawlowski.pizzatrattoria.dto.IngredientDTO;
import com.pawlowski.pizzatrattoria.entity.IngredientEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class IngredientMapper {

    private IngredientMapper() {
        //Class for utils only
    }

    public static List<IngredientDTO> mapToDTOs(List<IngredientEntity> ingredientEntities) {
        List<IngredientDTO> ingredientDTOS = new ArrayList<>(ingredientEntities.size());
        for(IngredientEntity ingredientEntity : ingredientEntities) {
            ingredientDTOS.add(mapToDTO(ingredientEntity));
        }
        return ingredientDTOS;
    }

    public static IngredientDTO mapToDTO(IngredientEntity ingredientEntity) {
        return IngredientDTO.builder()
                .id(ingredientEntity.getId())
                .price(ingredientEntity.getPrice())
                .name(ingredientEntity.getName())
                .build();
    }

    public static List<IngredientEntity> mapToEntities(List<IngredientDTO> ingredientDTOS) {
        List<IngredientEntity> ingredientEntities = new ArrayList<>();
        if(nonNull(ingredientDTOS) && !ingredientDTOS.isEmpty()) {
            for(IngredientDTO ingredientDTO : ingredientDTOS) {
                ingredientEntities.add(mapToEntity(ingredientDTO));
            }
        }
        return ingredientEntities;
    }

    public static IngredientEntity mapToEntity(IngredientDTO ingredientDTO) {
        return IngredientEntity.builder()
                .id(ingredientDTO.getId())
                .price(ingredientDTO.getPrice())
                .name(ingredientDTO.getName())
                .build();
    }

}
