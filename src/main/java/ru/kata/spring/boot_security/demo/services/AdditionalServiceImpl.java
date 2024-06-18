package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;

@Service
public class AdditionalServiceImpl implements AdditionalService {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdditionalServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void createModelForView(Model model, Principal principal) {
        model.addAttribute("authUser", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
    }
}
