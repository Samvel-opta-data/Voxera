package voxera.service;

import org.springframework.stereotype.Service;
import voxera.entity.User;
import voxera.repisotory.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class userService {

    private final UserRepository userRepository;

    public userService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
