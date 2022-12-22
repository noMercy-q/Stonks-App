package com.example.stonks.controller;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.stonks.dto.UserDto;
import com.example.stonks.model.User;
import com.example.stonks.service.UserService;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Validated @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model, 
                               HttpServletRequest request) {
        UserDto existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }
        userService.saveUser(userDto);

        try {
            request.login(userDto.getEmail(), userDto.getPassword());
        } catch (ServletException exception) {
            return "redirect:/login";
        }
        return "redirect:/";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
