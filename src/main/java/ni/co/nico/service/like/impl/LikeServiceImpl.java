package ni.co.nico.service.like.impl;

import lombok.RequiredArgsConstructor;
import ni.co.nico.domain.*;
import ni.co.nico.repository.board.BoardRepository;
import ni.co.nico.repository.like.LikeRepository;
import ni.co.nico.repository.reply.ReplyRepository;
import ni.co.nico.repository.user.UserRepository;
import ni.co.nico.service.like.LikeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;


    @Override
    public String likeOnOff(Long boardId, String userAddress) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            String result = "";
            Board board = boardOptional.get();
            //주어진 board와 userAddress로 like를 찾는다.
            Optional<LikeBoard> likeOptional = likeRepository.findByBoardIdAndUserAddress(boardId, userAddress);
            if (likeOptional.isPresent()) {
                // 좋아요 기록이 이미 있는 경우
                LikeBoard likeBoard = likeOptional.get();
                board.setLikeCount(board.getLikeCount() - 1);
                likeRepository.delete(likeBoard);
                result="off";
            } else {
                // 좋아요 기록이 없는 경우
                board.setLikeCount(board.getLikeCount() + 1);
                LikeBoard likeBoard = new LikeBoard();
                likeBoard.setBoardId(boardId);
                likeBoard.setUserAddress(userAddress);
                likeRepository.save(likeBoard);
                result= "on";

                if (board.getLikeCount() == 10 && !board.isWriterScoreReceived()) {
                    Optional<User> writerOptional = userRepository.findByAddress(board.getWriterAddress());
                    if (writerOptional.isPresent()) {
                        User writer = writerOptional.get();
                        writer.setScore(writer.getScore() + 500);
                        board.setWriterScoreReceived(true);
                        userRepository.save(writer);
                    }
                }

            }
            boardRepository.save(board);
            return result;
        } else {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다.");
        }
    }

}
