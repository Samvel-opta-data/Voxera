package voxera.repisotory;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
}