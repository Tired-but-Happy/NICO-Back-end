package ni.co.nico.repository.follow;

import ni.co.nico.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom{
    Optional<Follow> findByFolloweeAddressAndFollowerAddress(String followeeAddress, String followerAddress);
}
