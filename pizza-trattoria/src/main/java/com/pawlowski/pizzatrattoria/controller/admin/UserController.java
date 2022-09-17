package com.pawlowski.pizzatrattoria.controller.admin;

import com.pawlowski.pizzatrattoria.dto.OrderDTO;
import com.pawlowski.pizzatrattoria.dto.UserDTO;
import com.pawlowski.pizzatrattoria.service.OrderService;
import com.pawlowski.pizzatrattoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    private UserController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("users", userService.readAll());
        return "admin/users/users";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", UserDTO.builder().build());
        return "admin/users/users-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") UserDTO userDTO) {
        userService.create(userDTO);
        return "admin/users/users-confirmation";
    }

    @GetMapping("/read/{id}")
    public String read(Model model, @PathVariable String id) {
        UserDTO userDTO = userService.readById(Long.parseLong(id));
        model.addAttribute("user", userDTO);
        return "admin/users/users-read";
    }

    @GetMapping("/{id}/update")
    public String update(Model model, @PathVariable String id) {
        UserDTO user = UserDTO.builder().build();
        user.setId(Long.parseLong(id));
        model.addAttribute("user", user);
        return "admin/users/users-update";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute UserDTO userDTO) {
        userService.update(userDTO);
        return "admin/users/users-confirmation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        userService.deleteById(Long.parseLong(id));
        return "admin/users/users-confirmation";
    }

    @GetMapping("/{orderId}/add-user")
    public String addUser() {
        return "admin/orders/orders-add-user";
    }

    @PostMapping("/{orderId}/add-user")
    public String addUserToOrder(HttpServletRequest request, @PathVariable String orderId) {
        Long userId = Long.valueOf(request.getParameter("userId"));
        if (userService.checkUserInDatabase(userId)) {
            userService.addUserToOrder(userId, Long.parseLong(orderId));
            return "admin/orders/orders-confirmation";
        }
        return "admin/orders/orders-error-no-user";
    }

    @GetMapping("/delete-orphans")
    public String deleteOrphans() {
        userService.deleteOrdersWithoutUser();
        return "admin/orders/orders-confirmation";
    }

    @GetMapping("/read-order/{id}")
    public String readOrder(Model model, @PathVariable String id) {
        OrderDTO orderDTO = orderService.readById(Long.parseLong(id));
        model.addAttribute("order", orderDTO);
        return "admin/users/users-read-order";
    }

}
