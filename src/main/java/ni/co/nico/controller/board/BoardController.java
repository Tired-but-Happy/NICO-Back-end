package ni.co.nico.controller.board;

import io.jsonwebtoken.Jwts;
import ni.co.nico.domain.Board;
import ni.co.nico.dto.board.BoardCreateReqDTO;
import ni.co.nico.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //유저는 컨트랙트에 글을 남깁니다.
    //[구현해야함] 글을 쓰면 사용자의 score +10 로직
    @PostMapping(value = "/{contractAddress}")
    public ResponseEntity<String> createBoard(HttpServletRequest request,@PathVariable String contractAddress, @RequestBody BoardCreateReqDTO boardCreateReqDTO) {
        String content = boardCreateReqDTO.getContent();
        //헤더에 담긴 JWT 토큰에서 유저의 주소를 추출합니다.
        String userAddress = (Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws((request.getHeader("Authorization")).substring(7)).getBody()).getSubject();

        boardService.createBoard(contractAddress,userAddress,content);

        return ResponseEntity.ok("게시글이 생성되었습니다.");
    }

    //메인페이지에 노출되는 Like 높은 TOP3 Board 를 가져옵니다.
//    @GetMapping(value = "/top3")
//    public ResponseEntity<List<Board>>


}
