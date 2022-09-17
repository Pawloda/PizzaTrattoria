package com.pawlowski.pizzatrattoria.controller.user;

import com.pawlowski.pizzatrattoria.dto.OrderDTO;
import com.pawlowski.pizzatrattoria.dto.UserDTO;
import com.pawlowski.pizzatrattoria.entity.UserEntity;
import com.pawlowski.pizzatrattoria.service.OrderService;
import com.pawlowski.pizzatrattoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("user/process")
public class ProcessController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    private ProcessController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/number")
    public String number() {
        return "user/process/process-order-number";
    }

    @GetMapping("/create")
    public String create(HttpServletRequest request, Model model) {
        Long number = Long.valueOf(request.getParameter("number"));
        OrderDTO order = orderService.createEmptyOrder(number);
        model.addAttribute("order", order);
        return "user/process/process-order-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("order") OrderDTO orderDTO, @AuthenticationPrincipal UserDTO loggedUser) {
        orderDTO.setStatus("ready");
        OrderDTO order = orderService.create(orderDTO);
        userService.addUserToOrder(loggedUser.getId(), order.getId());
        return "user/process/process-confirmation";
    }

    @GetMapping("/read")
    public String read(Model model, @AuthenticationPrincipal UserDTO loggedUser) {
        UserDTO userDTO = userService.readById(loggedUser.getId());
        model.addAttribute("user", userDTO);
        return "user/process/process-user-read";
    }

    @GetMapping("/read-order/{id}")
    public String readOrder(Model model, @PathVariable String id) {
        OrderDTO orderDTO = orderService.readById(Long.parseLong(id));
        model.addAttribute("order", orderDTO);
        return "user/process/process-order-read";
    }

    @GetMapping("/update")
    public String update(Model model) {
        UserDTO user = UserDTO.builder().build();
        model.addAttribute("user", user);
        return "user/process/process-user-update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute UserDTO userDTO, @AuthenticationPrincipal UserDTO loggedUser) {
        userDTO.setId(loggedUser.getId());
        userDTO.setRole("USER");
        userService.update(userDTO);
        return "user/process/process-confirmation";
    }

}
