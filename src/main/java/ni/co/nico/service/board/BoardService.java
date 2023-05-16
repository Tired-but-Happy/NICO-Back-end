package ni.co.nico.service.board;

import ni.co.nico.domain.Board;
import ni.co.nico.dto.board.BoardCreateReqDTO;

import java.util.List;

public interface BoardService {
    public void createBoard(String contractAddress, String userAddress, String content);
    public Board getBoard(String userAddress);
    public List<Board> getBoardList(String userAddress);
    public List<Board> getTopRatedBoardList(String userAddress);
}
