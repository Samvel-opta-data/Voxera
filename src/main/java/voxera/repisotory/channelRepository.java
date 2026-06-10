package voxera.repisotory;

import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.channel;

public interface channelRepository extends JpaRepository<channel, Integer> {
}