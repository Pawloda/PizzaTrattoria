package com.pawlowski.pizzatrattoria.config;

import com.pawlowski.pizzatrattoria.dto.UserDTO;
import com.pawlowski.pizzatrattoria.service.UserService;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class TrattoriaAdministration {

    public TrattoriaAdministration(UserService userService) {
        userService.create(UserDTO.builder()
                .orders(Collections.emptyList())
                .id(1L)
                .email("trattoria@gmail.com")
                .firstName("Trattoria")
                .password("admin")
                .role("ADMIN")
                .secondName("Administrator")
                .username("admin")
                .build()
        );
    }

}
