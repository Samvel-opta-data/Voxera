package voxera.repisotory;

import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.user;

public interface userRepository extends JpaRepository<user, Integer> {
}
