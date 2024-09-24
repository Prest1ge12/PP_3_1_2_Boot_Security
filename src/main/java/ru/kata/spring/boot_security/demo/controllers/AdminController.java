package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAdminPanel(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "/admin/admin";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/admin/users";
    }

    @GetMapping("/user")
    public String getUser(Model model, @RequestParam("id") Long id) {
        User user = userService.getUserById(id); // Получаем пользователя по ID
        model.addAttribute("user", user); // Передаем его в модель
        model.addAttribute("userRoles", roleService.getUserRoles(id));
        return "/admin/user";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("availableRoles", roleService.getAvailableRoles());
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping("/new/create")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles", required = false) Set<Long> roles) {
        userService.saveUser(user, roles);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("availableRoles", roleService.getAvailableRoles());
        return "admin/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "roles", required = false) Set<Long> roles) {
        System.out.println("Обновление ролей пользователя: " + roles);
        userService.updateUser(user.getId(), user, roles);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin/users";

    }
}
