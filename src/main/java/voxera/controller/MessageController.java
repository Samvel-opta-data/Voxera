package voxera.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import voxera.entity.Message;
import voxera.entity.User;
import voxera.service.MessageService;
import voxera.service.UserService;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.findAll();
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public Message broadcastMessage(Message incomingMessage, Principal principal) {

        if (incomingMessage.getTimestamp() <= 0L) {
            incomingMessage.setTimestamp(Instant.now().toEpochMilli());
        }

        if (principal != null) {
            User sender = userService.findByUsername(principal.getName()).orElse(null);
            incomingMessage.setSender(sender);
        }

        return messageService.save(incomingMessage);
    }
}