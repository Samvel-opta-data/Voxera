package voxera.repisotory;

import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.message;

public interface messageRepository extends JpaRepository<message, Integer> {
}
