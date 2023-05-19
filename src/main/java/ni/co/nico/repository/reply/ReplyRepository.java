package ni.co.nico.repository.reply;

import ni.co.nico.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long>,ReplyRepositoryCustom {
    int countByBoardId(Long id);

    List<Reply> findByWriterAddress(String userAddress);
}
