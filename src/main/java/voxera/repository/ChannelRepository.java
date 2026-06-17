package voxera.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.Channel;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    List<Channel> findByCategoryId(Integer categoryId);
}