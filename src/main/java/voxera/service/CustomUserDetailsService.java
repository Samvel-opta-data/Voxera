package voxera.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import voxera.entity.User;
import voxera.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== LOGIN-VERSUCH FÜR USER: " + username + " ===");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("❌ FEHLER: User '" + username + "' wurde NICHT in der Datenbank gefunden!");
                    return new UsernameNotFoundException("User not found");
                });

        System.out.println("✅ USER GEFUNDEN!");
        System.out.println("DB-Username: " + user.getUsername());
        System.out.println("DB-Passwort-Hash: " + user.getPassword());
        System.out.println("DB-Rolle: " + user.getRole());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole())
                .build();
    }
}