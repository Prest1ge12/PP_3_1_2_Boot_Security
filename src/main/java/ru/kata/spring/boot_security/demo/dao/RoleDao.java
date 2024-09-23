package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    List<Role> getAvailableRoles();

    List<Role> getUserRoles(Long id);

    Role getRoleByID(Long id);
}
