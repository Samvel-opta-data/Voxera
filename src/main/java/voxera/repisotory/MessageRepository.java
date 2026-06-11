package voxera.repisotory;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
