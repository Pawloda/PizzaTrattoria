package com.pawlowski.pizzatrattoria.service;

import com.pawlowski.pizzatrattoria.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> readAll();

    UserDTO create(UserDTO userDTO);

    UserDTO readById(Long id);

    UserDTO update(UserDTO userDTO);

    void deleteById(Long id);

    void addUserToOrder(Long userId, Long orderId);

    void deleteOrdersWithoutUser();

    boolean checkUserInDatabase(Long id);

}
