package com.pawlowski.pizzatrattoria.controller;

import com.pawlowski.pizzatrattoria.dto.UserDTO;
import com.pawlowski.pizzatrattoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String welcome() {
        return "home/welcome";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", UserDTO.builder().build());
        return "home/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") UserDTO userDTO) {
        userDTO.setRole("USER");
        try {
            userService.create(userDTO);
        } catch(IllegalArgumentException exception) {
            return "home/username";
        }
        return "home/confirmation";
    }

    @GetMapping("/contact")
    public String contact() {
        return "home/contact";
    }

    @GetMapping("/contact-index")
    public String contactIndex() {
        return "home/contact-index";
    }

    @GetMapping("/index")
    public String index() {
        return "home/index";
    }

}
