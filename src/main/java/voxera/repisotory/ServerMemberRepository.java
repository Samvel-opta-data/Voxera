package voxera.repisotory;

import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.ServerMember;

public interface ServerMemberRepository extends JpaRepository<ServerMember, Integer> {
}
