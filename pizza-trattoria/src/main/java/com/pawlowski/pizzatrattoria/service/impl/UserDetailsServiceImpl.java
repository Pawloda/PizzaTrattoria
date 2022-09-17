package com.pawlowski.pizzatrattoria.service.impl;

import com.pawlowski.pizzatrattoria.entity.UserEntity;
import com.pawlowski.pizzatrattoria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pawlowski.pizzatrattoria.mapper.UserMapper.mapToDTO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        UserEntity userEntity = null;
        if(optional.isPresent()) {
            userEntity = optional.get();
        }
        return mapToDTO(userEntity);
    }

}
