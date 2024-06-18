package ru.kata.spring.boot_security.demo.services;

import org.springframework.ui.Model;

import java.security.Principal;

public interface AdditionalService {
    void createModelForView(Model model, Principal principal);
}
