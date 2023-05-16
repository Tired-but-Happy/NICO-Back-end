package ni.co.nico.repository.reply;

import ni.co.nico.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Long>,ReplyRepositoryCustom {
}
