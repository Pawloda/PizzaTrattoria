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
@Table(name = "trattoria_user")
public class UserEntity {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "trattoria_user_id")
    private List<OrderEntity> orders;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    private String password;
    private String role;

    @Column(name = "second_name")
    private String secondName;

    private String username;

}
