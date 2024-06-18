package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService, RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("all_users", userService.getAllUsers());
        return "admin/admin_home";
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/add_user";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("newUser") User newUser,
                          @RequestParam("roles") List<String> roles) {
        if (roles.contains("ROLE_USER")) {
            newUser.setAll_roles(roleService.getRole("ROLE_USER"));
        }
        if (roles.contains("ROLE_ADMIN")) {
            newUser.setAll_roles(roleService.getRole("ROLE_ADMIN"));
        }
        userService.saveUser(newUser);
        return "redirect:/admin";
    }

    @GetMapping("/show")
    public String showUserById(@RequestParam("id") int id, Model model) {
        User user = userService.showUserById(id);
        if (user == null) {
            return "redirect:/admin";
        } else {
            model.addAttribute("user", user);
            return "admin/selected_user";
        }
    }

    @GetMapping("/show/edit")
    public String editUser(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.showUserById(id));
        return "admin/edit_user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
        userService.updateUserById(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/show/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
