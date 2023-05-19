package ni.co.nico.service.board.impl;

import lombok.RequiredArgsConstructor;
import ni.co.nico.domain.Board;
import ni.co.nico.domain.User;
import ni.co.nico.dto.board.BoardCreateReqDTO;
import ni.co.nico.repository.board.BoardRepository;
import ni.co.nico.repository.user.UserRepository;
import ni.co.nico.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ni.co.nico.domain.Leveling;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;


    @Override
    public void createBoard(String contractAddress, String userAddress, String content) {
        Optional<User> userOptional = userRepository.findByAddress(userAddress);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Board board = new Board();
            board.setContractAddress(contractAddress);
            board.setWriterAddress(user.getAddress());
            board.setWriterNickName(user.getNickName());
            board.setContent(content);

            boardRepository.save(board);
            //글을 쓰면 10점을 부여.
            user.setScore(user.getScore() + 10);
            userRepository.save(user);


            Leveling leveling = new Leveling();
            int levelBefore = user.getLevel();
            int levelAfter = leveling.calculateLevel(user.getScore());

            // If the level has changed, update the user's level and other related information
            if (levelAfter > levelBefore) {
                user.setLevel(levelAfter);
            }
        } else {
            throw new IllegalArgumentException("사용자가 존재하지 않습니다.");
            // 예외 처리 또는 적절한 로직 추가
        }
    }

    @Override
    public Board getBoard(String userAddress) {
        return null;
    }

    @Override
    public List<Board> getBoardList(String userAddress) {
        return null;
    }

    @Override
    public List<Board> getTopRatedBoardList(String userAddress) {
        return null;
    }
}
