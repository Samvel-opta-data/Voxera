package voxera.controller;

import org.springframework.web.bind.annotation.*;
import voxera.entity.Server;
import voxera.service.ServerService;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    public List<Server> getAllServers() {
        return serverService.findAll();
    }

    @PostMapping
    public Server createServer(@RequestBody Server server) {
        return serverService.save(server);
    }
}
