package ni.co.nico.repository.like;

import ni.co.nico.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom{
    Optional<Like> findByBoardIdAndUserAddress(Long boardId, String userAddress);
}
