package com.pawlowski.pizzatrattoria.mapper;

import com.pawlowski.pizzatrattoria.dto.OrderDTO;
import com.pawlowski.pizzatrattoria.entity.OrderEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class OrderMapper {

    private OrderMapper() {
        //Class for utils only
    }

    public static List<OrderDTO> mapToDTOs(List<OrderEntity> orderEntities) {
        List<OrderDTO> orderDTOs = new ArrayList<>(orderEntities.size());
        if(!orderEntities.isEmpty()) {
            for(OrderEntity orderEntity : orderEntities) {
                orderDTOs.add(mapToDTO(orderEntity));
            }
        }
        return orderDTOs;
    }

    public static OrderDTO mapToDTO(OrderEntity orderEntity) {
        return OrderDTO.builder()
                .pizzas(PizzaMapper.mapToDTOs(orderEntity.getPizzas()))
                .id(orderEntity.getId())
                .price(orderEntity.getPrice())
                .customerAddress(orderEntity.getCustomerAddress())
                .customerEmail(orderEntity.getCustomerEmail())
                .customerFirstName(orderEntity.getCustomerFirstName())
                .customerNote(orderEntity.getCustomerNote())
                .customerSecondName(orderEntity.getCustomerSecondName())
                .customerPhone(orderEntity.getCustomerPhone())
                .status(orderEntity.getStatus())
                .build();
    }

    public static List<OrderEntity> mapToEntities(List<OrderDTO> orderDTOs) {
        List<OrderEntity> orderEntities = new ArrayList<>();
        if(nonNull(orderDTOs) && !orderDTOs.isEmpty()) {
            for(OrderDTO orderDTO : orderDTOs) {
                orderEntities.add(mapToEntity(orderDTO));
            }
        }
        return orderEntities;
    }

    public static OrderEntity mapToEntity(OrderDTO orderDTO) {
        return OrderEntity.builder()
                .pizzas(PizzaMapper.mapToEntities(orderDTO.getPizzas()))
                .id(orderDTO.getId())
                .price(orderDTO.getPrice())
                .customerAddress(orderDTO.getCustomerAddress())
                .customerEmail(orderDTO.getCustomerEmail())
                .customerFirstName(orderDTO.getCustomerFirstName())
                .customerNote(orderDTO.getCustomerNote())
                .customerSecondName(orderDTO.getCustomerSecondName())
                .customerPhone(orderDTO.getCustomerPhone())
                .status(orderDTO.getStatus())
                .build();
    }

}
