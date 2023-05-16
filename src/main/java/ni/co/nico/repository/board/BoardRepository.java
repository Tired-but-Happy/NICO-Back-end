package ni.co.nico.repository.board;

import ni.co.nico.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long>,BoardRepositoryCustom{

}
