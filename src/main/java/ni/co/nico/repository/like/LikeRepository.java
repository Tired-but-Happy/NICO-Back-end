package ni.co.nico.repository.like;

import ni.co.nico.domain.LikeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeBoard, Long>, LikeRepositoryCustom{
    Optional<LikeBoard> findByBoardIdAndUserAddress(Long boardId, String userAddress);

    int countByBoardId(Long id);
}
