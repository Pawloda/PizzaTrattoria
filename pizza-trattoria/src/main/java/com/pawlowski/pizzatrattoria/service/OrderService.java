package com.pawlowski.pizzatrattoria.service;

import com.pawlowski.pizzatrattoria.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> readAll();

    OrderDTO create(OrderDTO orderDTO);

    OrderDTO readById(Long id);

    OrderDTO update(OrderDTO orderDTO);

    void deleteById(Long id);

    void deletePizzasWithoutOrder();

    void deleteEmptyOrders();

    OrderDTO createEmptyOrder(Long number);

}
