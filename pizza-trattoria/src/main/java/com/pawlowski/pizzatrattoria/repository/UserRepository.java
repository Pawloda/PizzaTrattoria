package com.pawlowski.pizzatrattoria.repository;

import com.pawlowski.pizzatrattoria.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE trattoria_order SET trattoria_user_id = :userId WHERE id = :orderId", nativeQuery = true)
    void addUserToOrder(@Param("userId") Long userId, @Param("orderId") Long orderId);

    Optional<UserEntity> findByUsername(String username);

}
