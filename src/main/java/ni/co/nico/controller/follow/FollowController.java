package ni.co.nico.controller.follow;

import io.jsonwebtoken.Jwts;
import ni.co.nico.service.follow.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/follow")
@CrossOrigin(origins = {"http://localhost:3000"})
public class FollowController {

    private final FollowService followService;
    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{followeeAddress}")
    public ResponseEntity<String> createReply(HttpServletRequest request, @PathVariable String followeeAddress){
        String userAddress = (Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws((request.getHeader("Authorization")).substring(7)).getBody()).getSubject();

        String result=followService.followOnOff(followeeAddress,userAddress);

        return ResponseEntity.ok(result);
    }




}
