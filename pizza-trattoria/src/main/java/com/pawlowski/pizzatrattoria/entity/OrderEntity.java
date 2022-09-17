package com.pawlowski.pizzatrattoria.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trattoria_order")
public class OrderEntity {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "trattoria_order_id")
    private List<PizzaEntity> pizzas;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_first_name")
    private String customerFirstName;

    @Column(name = "customer_note")
    private String customerNote;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_second_name")
    private String customerSecondName;

    private String status;

}
