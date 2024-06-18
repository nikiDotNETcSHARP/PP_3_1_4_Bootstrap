package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.AdditionalService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.validators.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class AdminController {
    private final UserService userService;
    private final AdditionalService additionalService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService,
                           AdditionalService additionalService,
                           UserValidator userValidator) {
        this.userService = userService;
        this.additionalService = additionalService;
        this.userValidator = userValidator;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("newUser", new User());
        additionalService.createModelForView(model, principal);
        model.addAttribute("activeTab", "usersTable");
        return "adminPage";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult,
                          Principal principal, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            additionalService.createModelForView(model, principal);
            model.addAttribute("activeTab", "addUser");
            return "adminPage";
        }
        userService.saveUser(user);
        return "redirect:/users/admin";
    }

    @PatchMapping("/admin")
    public String updateUser(@ModelAttribute("userIter") @Valid User user,
                             BindingResult bindingResult,
                             Model model, Principal principal) {
        model.addAttribute("authUser", userService.findByUsername(principal.getName()));
        if (bindingResult.hasErrors()) {
            return "adminPage";
        }
        userService.updateUser(user);
        return "redirect:/users/admin";
    }

    @DeleteMapping("/admin")
    public String deleteUser(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.showUserById(id));
        userService.deleteById(id);
        return "redirect:/users/admin";
    }
}
