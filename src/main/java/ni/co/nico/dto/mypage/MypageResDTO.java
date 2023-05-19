package ni.co.nico.dto.mypage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MypageResDTO {
    String userAddress;
    String nickname;
    String profileImg;
    String styleName;
    int followerCount;
    int followingCount;

    int level;
    String introduction;
    int score;

}
