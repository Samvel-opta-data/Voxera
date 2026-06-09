package voxera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

    @GetMapping("/")
    public String home() {
        return "<h1> Willkommen auf Voxera!</h1>";
    }

    @GetMapping("/impressum")
    public String impressum() {
        return "<h1> Samvel, Dustin, Vladyslav</h1>";
    }
}
