package ru.kata.spring.boot_security.demo.controllers;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserSecurityDetails;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
//@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAdminPanel(Model model) {
        System.out.println("ПОЛУЧЕННЫЙ ID: " + userService); // Логирование id
        model.addAttribute("user", userService);
        return "admin";
    }

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/users";
    }


    @GetMapping("/admin/user")
    public String getUser(Model model, @RequestParam("id") Long id) {
        User user = userService.getUser(id); // Получаем пользователя по ID
        model.addAttribute("user", user); // Передаем его в модель
        model.addAttribute("userRoles", roleService.getUserRoles(id));
        return "/user";
    }

    @GetMapping("/user")
    public String getUserProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserSecurityDetails userDetails = (UserSecurityDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        model.addAttribute("user", currentUser);
        model.addAttribute("userRoles", roleService.getUserRoles(currentUser.getId()));
        return "user";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping("/admin/new/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("availableRoles", roleService.getAvailableRoles());
        return "admin/edit";
    }

    @PostMapping("/admin/update")
    public String update(@ModelAttribute("user") User user) {
        System.out.println("Обновление пользователя: " + user);
        userService.update(user.getId(), user);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/delete")
    public String delete(@ModelAttribute("user") User user) {
        userService.delete(user.getId());
        return "redirect:/admin/users";

    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/login";
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}