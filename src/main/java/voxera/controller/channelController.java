package voxera.controller;

import org.springframework.web.bind.annotation.*;
import voxera.entity.Channel;
import voxera.service.channelService;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
public class channelController {

    private final channelService channelService;

    public channelController(channelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping
    public List<Channel> getAllChannels(@RequestParam(required = false) Integer categoryId) {
        if (categoryId != null) {
            return channelService.findByCategoryId(categoryId);
        }
        return channelService.findAll();
    }

    @PostMapping
    public Channel createChannel(@RequestBody Channel channel) {
        return channelService.save(channel);
    }
}
