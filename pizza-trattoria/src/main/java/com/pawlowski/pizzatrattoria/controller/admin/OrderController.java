package com.pawlowski.pizzatrattoria.controller.admin;

import com.pawlowski.pizzatrattoria.dto.OrderDTO;
import com.pawlowski.pizzatrattoria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    private OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("orders", orderService.readAll());
        return "admin/orders/orders";
    }

    @GetMapping("/number")
    public String number() {
        return "admin/orders/orders-number";
    }

    @GetMapping("/create")
    public String create(HttpServletRequest request, Model model) {
        Long number = Long.valueOf(request.getParameter("number"));
        OrderDTO order = orderService.createEmptyOrder(number);
        model.addAttribute("order", order);
        return "admin/orders/orders-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("order") OrderDTO orderDTO) {
        orderService.create(orderDTO);
        return "admin/orders/orders-confirmation";
    }

    @GetMapping("/read/{id}")
    public String read(Model model, @PathVariable String id) {
        OrderDTO orderDTO = orderService.readById(Long.parseLong(id));
        model.addAttribute("order", orderDTO);
        return "admin/orders/orders-read";
    }

    @GetMapping("{id}/number")
    public String numberUpdate(Model model, @PathVariable String id) {
        model.addAttribute("id", id);
        return "admin/orders/orders-number-update";
    }

    @GetMapping("/{id}/update")
    public String update(HttpServletRequest request, Model model, @PathVariable String id) {
        Long number = Long.valueOf(request.getParameter("number"));
        OrderDTO order = orderService.createEmptyOrder(number);
        order.setId(Long.parseLong(id));
        model.addAttribute("order", order);
        return "admin/orders/orders-update";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute OrderDTO orderDTO) {
        orderService.update(orderDTO);
        return "admin/orders/orders-confirmation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        orderService.deleteById(Long.parseLong(id));
        return "admin/orders/orders-confirmation";
    }

    @GetMapping("/delete-empty")
    public String deleteEmpty() {
        orderService.deleteEmptyOrders();
        return "admin/orders/orders-confirmation";
    }

    @GetMapping("/delete-orphans")
    public String deleteOrphans() {
        orderService.deletePizzasWithoutOrder();
        return "admin/pizzas/pizzas-confirmation";
    }

}
