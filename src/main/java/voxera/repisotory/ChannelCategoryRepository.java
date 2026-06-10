package voxera.repisotory;

import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.ChannelCategory;

public interface ChannelCategoryRepository extends JpaRepository<ChannelCategory, Integer> {
}
