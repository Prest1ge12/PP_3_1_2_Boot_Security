package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImp;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private RoleDaoImp roleDaoImp;

    @Autowired
    public RoleServiceImp(RoleDaoImp rollDaoImp) {
        this.roleDaoImp = rollDaoImp;
    }

    @Override
    public List<Role> getAvailableRoles() {
        return roleDaoImp.getAvailableRoles();
    }

    @Override
    public List<Role> getUserRoles(Long id) {
        return roleDaoImp.getUserRoles(id);
    }

    @Override
    public List<Role> getRoleByID(Long id) {
        return getRoleByID(id);
    }
}
