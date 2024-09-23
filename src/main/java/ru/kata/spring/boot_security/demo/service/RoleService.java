package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAvailableRoles();

    List<Role> getUserRoles(Long id);

    List<Role> getRoleByID(Long id);
}
