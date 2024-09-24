package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// Сейчас не использую, но для следующей задачи оставил
@Controller
public class LoginController {

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
