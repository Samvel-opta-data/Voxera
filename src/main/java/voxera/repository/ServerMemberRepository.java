package voxera.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.ServerMember;

@Repository
public interface ServerMemberRepository extends JpaRepository<ServerMember, Integer> {
}
