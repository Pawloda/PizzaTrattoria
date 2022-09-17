package com.pawlowski.pizzatrattoria.service.impl;

import com.pawlowski.pizzatrattoria.dto.IngredientDTO;
import com.pawlowski.pizzatrattoria.dto.PizzaDTO;
import com.pawlowski.pizzatrattoria.entity.IngredientEntity;
import com.pawlowski.pizzatrattoria.entity.PizzaEntity;
import com.pawlowski.pizzatrattoria.mapper.IngredientMapper;
import com.pawlowski.pizzatrattoria.repository.IngredientRepository;
import com.pawlowski.pizzatrattoria.repository.PizzaRepository;
import com.pawlowski.pizzatrattoria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.pawlowski.pizzatrattoria.mapper.PizzaMapper.*;
import static java.util.Objects.nonNull;

@Service
public class PizzaServiceImpl implements PizzaService {

    private final IngredientRepository ingredientRepository;
    private final PizzaRepository pizzaRepository;

    @Autowired
    private PizzaServiceImpl(IngredientRepository ingredientRepository, PizzaRepository pizzaRepository) {
        this.ingredientRepository = ingredientRepository;
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    public List<PizzaDTO> readAll() {
        List<PizzaEntity> pizzaEntities = (List<PizzaEntity>) pizzaRepository.findAll();
        for(PizzaEntity pizzaEntity : pizzaEntities) {
            updatePrice(mapToDTO(pizzaEntity));
        }
        return mapToDTOs((List<PizzaEntity>) pizzaRepository.findAll());
    }

    @Override
    public PizzaDTO create(PizzaDTO pizzaDTO) {
        List<String> stringIngredients = pizzaDTO.getStringIngredients();
        List<IngredientEntity> ingredients = getIngredientsFromStrings(stringIngredients);
        PizzaEntity pizzaEntity = mapToEntity(pizzaDTO);
        pizzaEntity.setIngredients(ingredients);
        pizzaEntity.setPrice(calculatePrice(mapToDTO(pizzaEntity)));
        return mapToDTO(pizzaRepository.save(pizzaEntity));
    }

    @Override
    public PizzaDTO readById(Long id) {
        PizzaEntity pizzaEntity = getEntityById(id);
        return mapToDTO(pizzaEntity);
    }

    @Override
    public PizzaDTO update(PizzaDTO pizzaDTO) {
        List<String> stringIngredients = pizzaDTO.getStringIngredients();
        List<IngredientEntity> ingredients = getIngredientsFromStrings(stringIngredients);
        PizzaEntity pizzaEntityToUpdate = getEntityById(pizzaDTO.getId());
        List<Long> ingredientIds = getIngredientsIds(pizzaEntityToUpdate.getIngredients());
        pizzaEntityToUpdate.setIngredients(ingredients);
        pizzaEntityToUpdate.setPrice(calculatePrice(mapToDTO(pizzaEntityToUpdate)));
        pizzaEntityToUpdate.setName(pizzaDTO.getName());
        PizzaEntity pizza = pizzaRepository.save(pizzaEntityToUpdate);
        deleteIngredientsFromIdList(ingredientIds);
        return mapToDTO(pizza);
    }

    @Override
    public void deleteById(Long id) {
        PizzaEntity pizza = getEntityById(id);
        for(IngredientEntity ingredient : pizza.getIngredients()) {
            ingredientRepository.deleteById(ingredient.getId());
        }
        pizzaRepository.deleteById(id);
    }

    @Override
    public void deleteIngredientsWithoutPizza() {
        List<IngredientEntity> ingredients = (List<IngredientEntity>) ingredientRepository.findAll();
        ingredients.removeAll(findIngredientsWithPizza());
        for(IngredientEntity ingredient : ingredients) {
            ingredientRepository.deleteById(ingredient.getId());
        }
    }

    private void updatePrice(PizzaDTO pizzaDTO) {
        PizzaEntity pizzaEntityUpdated = getEntityById(pizzaDTO.getId());
        pizzaEntityUpdated.setPrice(calculatePrice(mapToDTO(pizzaEntityUpdated)));
        pizzaRepository.save(pizzaEntityUpdated);
    }

    private List<IngredientEntity> getIngredientsFromStrings(List<String> stringIngredients) {
        List<IngredientEntity> ingredients = new ArrayList<>();
        for(String stringIngredient : stringIngredients) {
            ingredients.add(getIngredientFromString(stringIngredient));
        }
        return ingredients;
    }

    private IngredientEntity getIngredientFromString(String stringIngredient) {
        IngredientEntity ingredientEntity = IngredientEntity.builder().name(stringIngredient).build();
        ingredientEntity.setPrice(calculatePrice(IngredientMapper.mapToDTO(ingredientEntity)));
        Long id = ingredientRepository.save(ingredientEntity).getId();
        Optional<IngredientEntity> optional = ingredientRepository.findById(id);
        IngredientEntity ingredient = null;
        if(optional.isPresent()) {
            ingredient = optional.get();
        }
        return ingredient;
    }

    private PizzaEntity getEntityById(Long id) {
        Optional<PizzaEntity> optional = pizzaRepository.findById(id);
        PizzaEntity pizzaEntity = null;
        if(optional.isPresent()) {
            pizzaEntity = optional.get();
        }
        return pizzaEntity;
    }

    private List<Long> getIngredientsIds(List<IngredientEntity> ingredients) {
        List<Long> ids = new ArrayList<>();
        for(IngredientEntity ingredient : ingredients) {
            ids.add(ingredient.getId());
        }
        return ids;
    }

    private void deleteIngredientsFromIdList(List<Long> ids) {
        for(Long id : ids) {
            ingredientRepository.deleteById(id);
        }
    }

    private List<IngredientEntity> findIngredientsWithPizza() {
        List<PizzaEntity> pizzas = (List<PizzaEntity>) pizzaRepository.findAll();
        List<IngredientEntity> ingredients = new ArrayList<>();
        for(PizzaEntity pizza : pizzas) {
            ingredients.addAll(pizza.getIngredients());
        }
        return ingredients;
    }

    private Double calculatePrice(PizzaDTO pizzaDTO) {
        String name = pizzaDTO.getName();
        double price = 22.50d;
        if(Objects.equals(name, "California") || Objects.equals(name, "Chicago")) {
            price = 24.50d;
        } else if(Objects.equals(name, "Greek") || Objects.equals(name, "Sicilian")) {
            price = 26.50d;
        }
        List<IngredientDTO> ingredients = pizzaDTO.getIngredients();
        if(nonNull(ingredients) && !ingredients.isEmpty()) {
            for(IngredientDTO ingredientDTO : ingredients) {
                price += calculatePrice(ingredientDTO);
            }
        }
        return price;
    }

    public Double calculatePrice(IngredientDTO ingredientDTO) {
        String name = ingredientDTO.getName();
        double price = 2.50d;
        if(Objects.equals(name, "chicken") || Objects.equals(name, "ham") ||
                Objects.equals(name, "pepperoni") || Objects.equals(name, "salami")) {
            price = 4.50d;
        }
        return price;
    }

}
