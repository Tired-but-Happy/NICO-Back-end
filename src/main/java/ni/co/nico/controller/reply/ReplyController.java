package ni.co.nico.controller.reply;

import io.jsonwebtoken.Jwts;
import ni.co.nico.dto.reply.ReplyCreateReqDTO;
import ni.co.nico.service.reply.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }


    //contractAddress,boardId를 입력받아 reply를 작성하는 api
    @PostMapping("/{contractAddress}/{boardId}")
    public ResponseEntity<String> createReply(HttpServletRequest request, @PathVariable String contractAddress, @PathVariable Long boardId, @RequestBody ReplyCreateReqDTO replyCreateReqDTO){
        String content = replyCreateReqDTO.getContent();
        String userAddress = (Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws((request.getHeader("Authorization")).substring(7)).getBody()).getSubject();
        replyService.createReply(contractAddress,boardId,userAddress,content);
        return ResponseEntity.ok("댓글이 작성되었습니다.");
    }
}
