package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    List<Role> getRole(String roleName);

    List<Role> findAll();

    List<Role> findAllById(List<Integer> ids);
}
