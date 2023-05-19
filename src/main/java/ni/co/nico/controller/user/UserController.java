package ni.co.nico.controller.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ni.co.nico.dto.user.UserLoginReqDTO;
import ni.co.nico.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/getget")
    public ResponseEntity<String> getget(){
        LOGGER.info("[토큰에서 정보빼오기] 로그인유저 이름 :{}","ㅎㅇ");
        return ResponseEntity.ok().body("하이");
    }


    //사용자가 지갑연동으로 로그인을 한다.
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserLoginReqDTO userLoginReqDTO){
        
        String address=userLoginReqDTO.getUserAddress();
        
        //db에 저장
        userService.login(address);
        
        String token = generateToken(userLoginReqDTO.getUserAddress());
        return ResponseEntity.ok().body(token);
    }

    //사용자의 헤더에서 토큰을 추출하여 토큰에 담겨있는 정보를 빼온다.
    @GetMapping("/some-endpoint")
    public String someEndpoint(HttpServletRequest request) {
        String token  = (request.getHeader("Authorization")).substring(7);
        String address = (Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws(token).getBody()).getSubject();
        // 헤더에서 토큰 값 추출
        // 토큰 디코드 및 처리 로직 추가
        return "Token에 담겨있는 address 주소 :" + address;
    }

    private String generateToken(String address) {
        String token = Jwts.builder()
                .setSubject(address)
                .signWith(SignatureAlgorithm.HS512, "yourSecretKey")
                .compact();
        return token;
    }

}
