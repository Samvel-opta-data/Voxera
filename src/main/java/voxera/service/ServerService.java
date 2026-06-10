package voxera.service;

import org.springframework.stereotype.Service;
import voxera.entity.Server;
import voxera.repisotory.ServerRepository;

import java.util.List;

@Service
public class ServerService {

    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public List<Server> findAll() {
        return serverRepository.findAll();
    }

    public Server save(Server server) {
        return serverRepository.save(server);
    }
}
