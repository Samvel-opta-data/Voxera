package voxera.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.ChannelCategory;

@Repository
public interface ChannelCategoryRepository extends JpaRepository<ChannelCategory, Integer> {
}
