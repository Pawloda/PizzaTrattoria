package com.pawlowski.pizzatrattoria.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class OrderDTO {

    private List<PizzaDTO> pizzas;
    private Long id;
    private Double price;
    private String customerAddress;
    private String customerEmail;
    private String customerFirstName;
    private String customerNote;
    private String customerPhone;
    private String customerSecondName;
    private String status;

}
