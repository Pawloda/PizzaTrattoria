package com.pawlowski.pizzatrattoria.controller.admin;

import com.pawlowski.pizzatrattoria.dto.PizzaDTO;
import com.pawlowski.pizzatrattoria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    @Autowired
    private PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("pizzas", pizzaService.readAll());
        return "admin/pizzas/pizzas";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new PizzaDTO());
        return "admin/pizzas/pizzas-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("pizza") PizzaDTO pizzaDTO) {
        pizzaService.create(pizzaDTO);
        return "admin/pizzas/pizzas-confirmation";
    }

    @GetMapping("/read/{id}")
    public String read(Model model, @PathVariable String id) {
        PizzaDTO pizzaDTO = pizzaService.readById(Long.parseLong(id));
        model.addAttribute("pizza", pizzaDTO);
        return "admin/pizzas/pizzas-read";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable String id) {
        model.addAttribute("id", id);
        model.addAttribute("pizza", PizzaDTO.builder().id(Long.valueOf(id)).build());
        return "admin/pizzas/pizzas-update";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute PizzaDTO pizzaDTO) {
        pizzaService.update(pizzaDTO);
        return "admin/pizzas/pizzas-confirmation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        pizzaService.deleteById(Long.parseLong(id));
        return "admin/pizzas/pizzas-confirmation";
    }

    @GetMapping("/delete-orphans")
    public String deleteOrphans() {
        pizzaService.deleteIngredientsWithoutPizza();
        return "admin/ingredients/ingredients-confirmation";
    }

}
