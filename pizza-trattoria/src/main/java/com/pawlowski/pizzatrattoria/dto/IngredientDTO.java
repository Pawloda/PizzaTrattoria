package com.pawlowski.pizzatrattoria.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class IngredientDTO implements Serializable {

    private Long id;
    private Double price;
    private String name;

}
