package com.pawlowski.pizzatrattoria.service.impl;

import com.pawlowski.pizzatrattoria.dto.IngredientDTO;
import com.pawlowski.pizzatrattoria.entity.IngredientEntity;
import com.pawlowski.pizzatrattoria.mapper.IngredientMapper;
import com.pawlowski.pizzatrattoria.repository.IngredientRepository;
import com.pawlowski.pizzatrattoria.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.pawlowski.pizzatrattoria.mapper.IngredientMapper.mapToDTO;
import static com.pawlowski.pizzatrattoria.mapper.IngredientMapper.mapToEntity;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    private IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<IngredientDTO> readAll() {
        return IngredientMapper.mapToDTOs((List<IngredientEntity>) ingredientRepository.findAll());
    }

    @Override
    public IngredientDTO create(IngredientDTO ingredientDTO) {
        ingredientDTO.setPrice(calculatePrice(ingredientDTO));
        return mapToDTO(ingredientRepository.save(mapToEntity(ingredientDTO)));
    }

    @Override
    public IngredientDTO readById(Long id) {
        Optional<IngredientEntity> optional = ingredientRepository.findById(id);
        IngredientEntity ingredientEntity = null;
        if(optional.isPresent()) {
            ingredientEntity = optional.get();
        }
        assert ingredientEntity != null;
        return mapToDTO(ingredientEntity);
    }

    @Override
    public IngredientDTO update(IngredientDTO ingredientDTO) {
        IngredientDTO updatedIngredientDTO = readById(ingredientDTO.getId());
        updatedIngredientDTO.setPrice(calculatePrice(updatedIngredientDTO));
        updatedIngredientDTO.setName(ingredientDTO.getName());
        return create(updatedIngredientDTO);
    }

    @Override
    public void deleteById(Long id) {
        ingredientRepository.deleteById(id);
    }

    private Double calculatePrice(IngredientDTO ingredientDTO) {
        String name = ingredientDTO.getName();
        double price = 2.50d;
        if(Objects.equals(name, "chicken") || Objects.equals(name, "ham") ||
                Objects.equals(name, "pepperoni") || Objects.equals(name, "salami")) {
            price = 4.50d;
        }
        return price;
    }

}
