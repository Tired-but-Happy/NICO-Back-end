package ni.co.nico.repository.board;

import ni.co.nico.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>,BoardRepositoryCustom{

    List<Board> findByWriterAddress(String userAddress);
}
