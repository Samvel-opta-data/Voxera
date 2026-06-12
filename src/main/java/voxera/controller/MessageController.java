package voxera.controller;

import org.springframework.web.bind.annotation.*;
import voxera.entity.Message;

@RestController
@RequestMapping("/api/chat")

public class MessageController {
    @PostMapping("/send")
    public void sendMessage(@RequestBody Message message) {
        // Log the incoming Message entity. Use toString() to avoid depending on a specific getter name.
        System.out.println(message);
    }
}
