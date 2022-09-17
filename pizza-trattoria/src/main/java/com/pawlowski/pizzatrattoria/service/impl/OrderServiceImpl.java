package com.pawlowski.pizzatrattoria.service.impl;

import com.pawlowski.pizzatrattoria.dto.OrderDTO;
import com.pawlowski.pizzatrattoria.dto.PizzaDTO;
import com.pawlowski.pizzatrattoria.entity.OrderEntity;
import com.pawlowski.pizzatrattoria.entity.PizzaEntity;
import com.pawlowski.pizzatrattoria.repository.OrderRepository;
import com.pawlowski.pizzatrattoria.repository.PizzaRepository;
import com.pawlowski.pizzatrattoria.service.OrderService;
import com.pawlowski.pizzatrattoria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pawlowski.pizzatrattoria.mapper.OrderMapper.*;
import static java.util.Objects.isNull;

@Service
public class OrderServiceImpl implements OrderService {

    private final PizzaRepository pizzaRepository;
    private final PizzaService pizzaService;
    private final OrderRepository orderRepository;

    @Autowired
    private OrderServiceImpl(PizzaRepository pizzaRepository, PizzaService pizzaService, OrderRepository orderRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaService = pizzaService;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDTO> readAll() {
        List<OrderEntity> orderEntities = (List<OrderEntity>) orderRepository.findAll();
        for(OrderEntity orderEntity : orderEntities) {
            updatePrice(mapToDTO(orderEntity));
        }
        return mapToDTOs((List<OrderEntity>) orderRepository.findAll());
    }

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        List<PizzaEntity> pizzas = getPizzasFromDTO(orderDTO);
        OrderEntity orderEntity = mapToEntity(orderDTO);
        orderEntity.setPizzas(pizzas);
        orderEntity.setPrice(calculatePrice(orderEntity));
        return mapToDTO(orderRepository.save(orderEntity));
    }

    @Override
    public OrderDTO readById(Long id) {
        OrderEntity orderEntity = getEntityById(id);
        return mapToDTO(orderEntity);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        OrderEntity orderEntity = getEntityById(orderDTO.getId());
        List<Long> orderIds = getPizzasIds(orderEntity.getPizzas());
        orderEntity.setPizzas(getPizzasFromDTO(orderDTO));
        orderEntity.setPrice(calculatePrice(orderEntity));
        orderEntity.setCustomerAddress(orderDTO.getCustomerAddress());
        orderEntity.setCustomerEmail(orderDTO.getCustomerEmail());
        orderEntity.setCustomerFirstName(orderDTO.getCustomerFirstName());
        orderEntity.setCustomerNote(orderDTO.getCustomerNote());
        orderEntity.setCustomerPhone(orderDTO.getCustomerPhone());
        orderEntity.setCustomerSecondName(orderDTO.getCustomerSecondName());
        orderEntity.setStatus(orderDTO.getStatus());
        OrderEntity order = orderRepository.save(orderEntity);
        deletePizzasFromIdList(orderIds);
        return mapToDTO(order);
    }

    @Override
    public void deleteById(Long id) {
        OrderEntity order = getEntityById(id);
        for(PizzaEntity pizza : order.getPizzas()) {
            pizzaService.deleteById(pizza.getId());
        }
        orderRepository.deleteById(id);
    }

    @Override
    public void deletePizzasWithoutOrder() {
        List<PizzaEntity> pizzas = (List<PizzaEntity>) pizzaRepository.findAll();
        pizzas.removeAll(findPizzasWithOrder());
        for(PizzaEntity pizza : pizzas) {
            pizzaService.deleteById(pizza.getId());
        }
    }

    @Override
    public void deleteEmptyOrders() {
        List<OrderEntity> orders = (List<OrderEntity>) orderRepository.findAll();
        for(OrderEntity order : orders) {
            if(isNull(order.getPizzas()) || order.getPizzas().isEmpty()) {
                orderRepository.deleteById(order.getId());
            }
        }
    }

    @Override
    public OrderDTO createEmptyOrder(Long number) {
        List<PizzaDTO> pizzas = new ArrayList<>();
        for(int i = 0; i < number; i++) {
            pizzas.add(new PizzaDTO());
        }
        return OrderDTO.builder().pizzas(pizzas).build();
    }

    private void updatePrice(OrderDTO order) {
        OrderEntity orderEntityUpdated = getEntityById(order.getId());
        orderEntityUpdated.setPrice(calculatePrice(orderEntityUpdated));
        orderRepository.save(orderEntityUpdated);
    }

    private List<PizzaEntity> getPizzasFromDTO(OrderDTO order) {
        List<PizzaEntity> pizzas = new ArrayList<>();
        for(PizzaDTO pizza : order.getPizzas()) {
            pizzas.add(getPizzaFromDTO(pizza));
        }
        return pizzas;
    }

    private PizzaEntity getPizzaFromDTO(PizzaDTO pizzaDTO) {
        Long id = pizzaService.create(pizzaDTO).getId();
        Optional<PizzaEntity> optional = pizzaRepository.findById(id);
        PizzaEntity pizza = null;
        if(optional.isPresent()) {
            pizza = optional.get();
        }
        return pizza;
    }

    private Double calculatePrice(OrderEntity order) {
        Double price = 5d;
        List<PizzaEntity> pizzas = order.getPizzas();
        for(PizzaEntity pizza : pizzas) {
            price += pizza.getPrice();
        }
        return price;
    }

    private void deletePizzasFromIdList(List<Long> ids) {
        for(Long id : ids) {
            pizzaService.deleteById(id);
        }
    }

    private OrderEntity getEntityById(Long id) {
        Optional<OrderEntity> optional = orderRepository.findById(id);
        OrderEntity orderEntity = null;
        if(optional.isPresent()) {
            orderEntity = optional.get();
        }
        return orderEntity;
    }

    private List<Long> getPizzasIds(List<PizzaEntity> pizzas) {
        List<Long> ids = new ArrayList<>();
        for(PizzaEntity pizza : pizzas) {
            ids.add(pizza.getId());
        }
        return ids;
    }

    private List<PizzaEntity> findPizzasWithOrder() {
        List<OrderEntity> orders = (List<OrderEntity>) orderRepository.findAll();
        List<PizzaEntity> pizzas = new ArrayList<>();
        for(OrderEntity order : orders) {
            pizzas.addAll(order.getPizzas());
        }
        return pizzas;
    }

}
