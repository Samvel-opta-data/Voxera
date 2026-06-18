package voxera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "Homepage";
    }

    @GetMapping("/impressum")
    public String impressum() {
        return "Impressum";
    }

    @GetMapping("/tutorial")
    public String tutorial() {
        return "Tutorial";
    }

    @GetMapping("/chat")
    public String chat() {
        return "Chat";
    }

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @GetMapping("/register")
    public String register() {
        return "Register";
    }
}