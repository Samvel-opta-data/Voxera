package voxera.repisotory;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.Server;

@Repository
public interface ServerRepository extends JpaRepository<Server, Integer> {
}
