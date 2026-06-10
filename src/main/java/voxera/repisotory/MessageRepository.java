package voxera.repisotory;

import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
