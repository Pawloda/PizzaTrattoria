package com.pawlowski.pizzatrattoria.controller.admin;

import com.pawlowski.pizzatrattoria.dto.IngredientDTO;
import com.pawlowski.pizzatrattoria.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/ingredients")
@Controller
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    private IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("ingredients", ingredientService.readAll());
        return "admin/ingredients/ingredients";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredient", new IngredientDTO());
        return "admin/ingredients/ingredients-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("ingredient") IngredientDTO ingredientDTO) {
        ingredientService.create(ingredientDTO);
        return "admin/ingredients/ingredients-confirmation";
    }

    @GetMapping("/read/{id}")
    public String read(Model model, @PathVariable String id) {
        IngredientDTO ingredientDTO = ingredientService.readById(Long.parseLong(id));
        model.addAttribute("ingredient", ingredientDTO);
        return "admin/ingredients/ingredients-read";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable String id) {
        model.addAttribute("id", id);
        model.addAttribute("ingredient", IngredientDTO.builder().id(Long.valueOf(id)).build());
        return "admin/ingredients/ingredients-update";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute IngredientDTO ingredientDTO) {
        ingredientService.update(ingredientDTO);
        return "admin/ingredients/ingredients-confirmation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        ingredientService.deleteById(Long.parseLong(id));
        return "admin/ingredients/ingredients-confirmation";
    }

}

