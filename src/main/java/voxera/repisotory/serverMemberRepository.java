package voxera.repisotory;

import org.springframework.data.jpa.repository.JpaRepository;
import voxera.entity.serverMember;

public interface serverMemberRepository extends JpaRepository<serverMember, Integer> {
}
