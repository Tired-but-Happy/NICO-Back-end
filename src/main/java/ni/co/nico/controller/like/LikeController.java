package ni.co.nico.controller.like;

import io.jsonwebtoken.Jwts;
import ni.co.nico.dto.reply.ReplyCreateReqDTO;
import ni.co.nico.service.like.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/like")
@CrossOrigin(origins = {"http://localhost:3000"})
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<String> createReply(HttpServletRequest request, @PathVariable Long boardId){
        String userAddress = (Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws((request.getHeader("Authorization")).substring(7)).getBody()).getSubject();

        String result=likeService.likeOnOff(boardId,userAddress);

        return ResponseEntity.ok(result);
    }



}
