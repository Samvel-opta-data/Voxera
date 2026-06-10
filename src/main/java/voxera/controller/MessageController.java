package voxera.controller;

import org.springframework.web.bind.annotation.*;
import voxera.entity.Message;
import voxera.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.findAll();
    }
}
