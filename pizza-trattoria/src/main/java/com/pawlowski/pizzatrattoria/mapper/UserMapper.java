package com.pawlowski.pizzatrattoria.mapper;

import com.pawlowski.pizzatrattoria.dto.UserDTO;
import com.pawlowski.pizzatrattoria.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class UserMapper {

    private UserMapper() {
        //Class for utils only
    }

    public static List<UserDTO> mapToDTOs(List<UserEntity> userEntities) {
        List<UserDTO> userDTOs = new ArrayList<>(userEntities.size());
        for(UserEntity userEntity : userEntities) {
            userDTOs.add(mapToDTO(userEntity));
        }
        return userDTOs;
    }

    public static UserDTO mapToDTO(UserEntity userEntity) {
        if(nonNull(userEntity)) {
            return UserDTO.builder()
                    .orders(OrderMapper.mapToDTOs(userEntity.getOrders()))
                    .id(userEntity.getId())
                    .email(userEntity.getEmail())
                    .firstName(userEntity.getFirstName())
                    .password(userEntity.getPassword())
                    .secondName(userEntity.getSecondName())
                    .role(userEntity.getRole())
                    .username(userEntity.getUsername())
                    .build();
        }
        return null;
    }

    public static List<UserEntity> mapToEntities(List<UserDTO> userDTOs) {
        List<UserEntity> userEntities = new ArrayList<>();
        if(nonNull(userDTOs) && !userDTOs.isEmpty()) {
            for(UserDTO userDTO : userDTOs) {
                userEntities.add(mapToEntity(userDTO));
            }
        }
        return userEntities;
    }

    public static UserEntity mapToEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .orders(OrderMapper.mapToEntities(userDTO.getOrders()))
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .password(userDTO.getPassword())
                .secondName(userDTO.getSecondName())
                .role(userDTO.getRole())
                .username(userDTO.getUsername())
                .build();
    }

}
