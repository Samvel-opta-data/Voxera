package voxera.repisotory;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
