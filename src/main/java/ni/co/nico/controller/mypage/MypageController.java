package ni.co.nico.controller.mypage;

import io.jsonwebtoken.Jwts;
import ni.co.nico.dto.mypage.MyboardResDTO;
import ni.co.nico.dto.mypage.MypageResDTO;
import ni.co.nico.dto.mypage.MyreplyResDTO;
import ni.co.nico.dto.mypage.UsedAppDTO;
import ni.co.nico.dto.user.UserUpdateReqDTO;
import ni.co.nico.service.Blockchain.UsedAppService;
import ni.co.nico.service.mypage.MypageService;
import ni.co.nico.service.style.StyleService;
import ni.co.nico.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService myPageService;
    private final UsedAppService usedAppService;
    private final StyleService styleService;
    private final UserService userService;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public MypageController(MypageService myPageService, UsedAppService usedAppService, StyleService styleService, UserService userService) {
        this.myPageService = myPageService;
        this.usedAppService = usedAppService;
        this.styleService = styleService;
        this.userService = userService;
    }

    @GetMapping("/{userAddress}")
    public ResponseEntity<Object> getMyPageInfo(@PathVariable String userAddress) {
        // NEAR api 를 통해 사용자가 요청한 dapp의 정보를 db에 저장합니다.
//        try {
//            String url = "https://api.pikespeak.ai/account/transactions/" + userAddress;
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("accept", "application/json");
//            headers.set("x-api-key", "20e2fecb-7f5d-4a7d-ad60-6751196ec5ce");
//
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//            String json = responseEntity.getBody();
//            usedAppService.processTransactionData(json, userAddress);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 예외 처리 로직 추가 및 적절한 오류 응답 반환
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }

        // 사용자 정보를 가져옵니다.
        MypageResDTO mypageResDTO = myPageService.getMyPageInfo(userAddress);
        // 게시물 목록을 가져옵니다.
        List<MyboardResDTO> myBoardList = myPageService.getMyBoardList(userAddress);
        // 댓글 목록을 가져옵니다.
        List<MyreplyResDTO> myReplyList = myPageService.getMyReplyList(userAddress);
        // UsedApp 목록을 가져옵니다.
        List<UsedAppDTO> usedAppList = usedAppService.getTopUsedAppsByUser(userAddress);

        // 응답에 필요한 정보를 직접 생성하고 설정합니다.
        Map<String, Object> response = new HashMap<>();
        response.put("mypageInfo", mypageResDTO);
        response.put("boardList", myBoardList);
        response.put("replyList", myReplyList);
        response.put("usedAppList", usedAppList);

        // JSON 형태로 응답합니다.
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/set/{userAddress}")
    public void setOftenUsedAppDBFirst(@PathVariable String userAddress) {
        // NEAR api 를 통해 사용자가 요청한 dapp의 정보를 db에 저장합니다.
        try {
            String url = "https://api.pikespeak.ai/account/transactions/" + userAddress;

            HttpHeaders headers = new HttpHeaders();
            headers.set("accept", "application/json");
            headers.set("x-api-key", "20e2fecb-7f5d-4a7d-ad60-6751196ec5ce");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String json = responseEntity.getBody();
            usedAppService.processTransactionData(json, userAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @GetMapping("/near/{userAddress}")
    public ResponseEntity<Void> getUsedAppList(@PathVariable String userAddress) {
        try {
            String url = "https://api.pikespeak.ai/account/transactions/" + userAddress;

            HttpHeaders headers = new HttpHeaders();
            headers.set("accept", "application/json");
            headers.set("x-api-key", "20e2fecb-7f5d-4a7d-ad60-6751196ec5ce");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String json = responseEntity.getBody();
            usedAppService.processTransactionData(json, userAddress);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리 로직 추가 및 적절한 오류 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/update/{userAddress}")
    public ResponseEntity<List<String>> showStyleList(HttpServletRequest request) {
        String userAddress = (Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws((request.getHeader("Authorization")).substring(7)).getBody()).getSubject();

        List<String> styleList=styleService.getStyleList(userAddress);
        return ResponseEntity.ok(styleList);
    }

    @PutMapping(value = "/update/{userAddress}")
    public ResponseEntity<String> updateStyle(@PathVariable String userAddress, @RequestBody UserUpdateReqDTO userUpdateReqDTO) {
        userService.updateUserInfo(userAddress, userUpdateReqDTO);

        return ResponseEntity.ok("success");
    }

}
