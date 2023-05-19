package ni.co.nico.dto.mypage;

import lombok.Getter;
import lombok.Setter;

@Setter
public class MyreplyResDTO {
    Long replyId;
    Long boardId;
    String content;
    //x시간 전
    String registrationTimeAgo;
    //int likeCount;
}
