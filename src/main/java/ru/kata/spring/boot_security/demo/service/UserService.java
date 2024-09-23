package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User getUser(Long id);

    void saveUser(User user);

    void update(Long id, User updateUser);

    void delete(Long id);

}
