package voxera.repisotory;

import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
}