package ni.co.nico.dto.mypage;

import lombok.Setter;

@Setter
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
