package com.pawlowski.pizzatrattoria.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class PizzaDTO implements Serializable {

    private List<IngredientDTO> ingredients;
    private Long id;
    private Double price;
    private String name;
    private List<String> stringIngredients;

}
