package com.pawlowski.pizzatrattoria.service.impl;

import com.pawlowski.pizzatrattoria.dto.OrderDTO;
import com.pawlowski.pizzatrattoria.dto.UserDTO;
import com.pawlowski.pizzatrattoria.entity.OrderEntity;
import com.pawlowski.pizzatrattoria.entity.UserEntity;
import com.pawlowski.pizzatrattoria.mapper.OrderMapper;
import com.pawlowski.pizzatrattoria.repository.UserRepository;
import com.pawlowski.pizzatrattoria.service.OrderService;
import com.pawlowski.pizzatrattoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.pawlowski.pizzatrattoria.mapper.UserMapper.*;

@Service
public class UserServiceImpl implements UserService {

    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    private UserServiceImpl(OrderService orderService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> readAll() {
        return mapToDTOs((List<UserEntity>) userRepository.findAll());
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        checkUsername(userDTO.getUsername());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return mapToDTO(userRepository.save(mapToEntity(userDTO)));
    }

    @Override
    public UserDTO readById(Long id) {
        UserEntity userEntity = getEntityById(id);
        return mapToDTO(userEntity);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        UserEntity userEntity = getEntityById(userDTO.getId());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setRole(userDTO.getRole());
        userEntity.setSecondName(userDTO.getSecondName());
        UserEntity user = userRepository.save(userEntity);
        return mapToDTO(user);
    }

    @Override
    public void deleteById(Long id) {
        UserEntity user = getEntityById(id);
        for(OrderEntity order : user.getOrders()) {
            orderService.deleteById(order.getId());
        }
        userRepository.deleteById(id);
    }

    @Override
    public void addUserToOrder(Long userId, Long orderId) {
        userRepository.addUserToOrder(userId, orderId);
    }

    @Override
    public void deleteOrdersWithoutUser() {
        List<OrderDTO> orders = orderService.readAll();
        orders.removeAll(findOrdersWithUser());
        for(OrderDTO order : orders) {
            orderService.deleteById(order.getId());
        }
    }

    @Override
    public boolean checkUserInDatabase(Long id) {
        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
        for(UserEntity user : users) {
            if(Objects.equals(user.getId(), id)) {
                return true;
            }
        }
        return false;
    }

    private void checkUsername(String username) {
        if(!Objects.equals("admin", username)) {
            List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
            for(UserEntity user : users) {
                if(Objects.equals(user.getUsername(), username)) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private UserEntity getEntityById(Long id) {
        Optional<UserEntity> optional = userRepository.findById(id);
        UserEntity userEntity = null;
        if(optional.isPresent()) {
            userEntity = optional.get();
        }
        return userEntity;
    }

    private List<OrderDTO> findOrdersWithUser() {
        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
        List<OrderEntity> orders = new ArrayList<>();
        for(UserEntity user : users) {
            orders.addAll(user.getOrders());
        }
        return OrderMapper.mapToDTOs(orders);
    }

}
