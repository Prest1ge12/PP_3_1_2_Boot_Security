package ru.kata.spring.boot_security.demo.dao;


import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserDao {
    List<User> getAllUsers();

    User getUser(Long id);

    void saveUser(User user);

    void update(Long id, User updateUser);

    void delete(Long id);

    User findByUsername(String name);

}
