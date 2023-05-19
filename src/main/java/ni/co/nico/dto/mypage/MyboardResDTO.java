package ni.co.nico.dto.mypage;

import lombok.Setter;

@Setter
public class MyboardResDTO{
    String boardId;
    String contractAddress;
    String content;
    //x시간 전
    String registrationTimeAgo;
    int likeCount;
    int replyCount;
}

