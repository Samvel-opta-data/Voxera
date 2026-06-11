package voxera.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voxera.entity.User;
import voxera.service.userService;

@RestController
@RequestMapping("/api/auth")
public class authController {

    private final userService userService;

    public authController(userService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.save(user));
    }
}
