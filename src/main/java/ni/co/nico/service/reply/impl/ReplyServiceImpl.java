package ni.co.nico.service.reply.impl;

import lombok.RequiredArgsConstructor;
import ni.co.nico.domain.Board;
import ni.co.nico.domain.Reply;
import ni.co.nico.domain.User;
import ni.co.nico.repository.board.BoardRepository;
import ni.co.nico.repository.reply.ReplyRepository;
import ni.co.nico.repository.user.UserRepository;
import ni.co.nico.service.reply.ReplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public void createReply(String contractAddress, Long boardId, String writerAddress, String content) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        User user = userRepository.findByAddress(writerAddress)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Reply reply = new Reply();
        reply.setBoardId(boardId);
        reply.setContent(content);
        reply.setWriterAddress(writerAddress);
        reply.setWriterNickName(user.getNickName());

        replyRepository.save(reply);
    }
}
