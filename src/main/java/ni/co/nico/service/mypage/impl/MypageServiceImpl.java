package ni.co.nico.service.mypage.impl;

import lombok.RequiredArgsConstructor;
import ni.co.nico.domain.Board;
import ni.co.nico.domain.Reply;
import ni.co.nico.domain.User;
import ni.co.nico.dto.mypage.MyboardResDTO;
import ni.co.nico.dto.mypage.MypageResDTO;
import ni.co.nico.dto.mypage.MyreplyResDTO;
import ni.co.nico.repository.board.BoardRepository;
import ni.co.nico.repository.follow.FollowRepository;
import ni.co.nico.repository.like.LikeRepository;
import ni.co.nico.repository.reply.ReplyRepository;
import ni.co.nico.repository.user.UserRepository;
import ni.co.nico.service.mypage.MypageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;


    @Override
    public MypageResDTO getMyPageInfo(String userAddress) {
        MypageResDTO mypageResDTO = new MypageResDTO();

        // 사용자의 주소를 기반으로 데이터베이스에서 정보를 가져와서 설정합니다.
        // 예시로 각 필드에 적절한 값을 할당하도록 구현했습니다.
        Optional<User> user = userRepository.findByAddress(userAddress); // 데이터베이스에서 사용자 정보를 조회하는 예시 코드
        if (user.isPresent()) {
            User userData = user.get();
            mypageResDTO.setUserAddress(userData.getAddress());
            mypageResDTO.setNickname(userData.getNickName());
            mypageResDTO.setStyleName(userData.getStyle());
            // mypageResDTO.setProfileImg(userData.getProfileImg()); // 프로필 이미지를 가져오는 코드

            // 팔로워 수와 팔로잉 수는 Follow 엔티티를 조회하여 계산합니다.
            int followerCount = followRepository.countByFolloweeAddress(userAddress); // 팔로워 수를 조회하는 예시 코드
            int followingCount = followRepository.countByFollowerAddress(userAddress); // 팔로잉 수를 조회하는 예시 코드
            mypageResDTO.setFollowerCount(followerCount);
            mypageResDTO.setFollowingCount(followingCount);
            mypageResDTO.setLevel(userData.getLevel());
            mypageResDTO.setIntroduction(userData.getIntroduction());
            mypageResDTO.setScore(userData.getScore());
        }
        return mypageResDTO;
    }

    public String getRegistrationTimeAgo(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);
        long hours = duration.toHours();

        if (hours < 1) {
            long minutes = duration.toMinutes();
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else {
            long days = duration.toDays();
            return days + "일 전";
        }
    }

    @Override
    public List<MyboardResDTO> getMyBoardList(String userAddress) {
        List<MyboardResDTO> myBoardList = new ArrayList<>();

        // 사용자의 주소를 기반으로 데이터베이스에서 해당 사용자의 게시물 목록을 조회합니다.
        List<Board> boardList = boardRepository.findByWriterAddress(userAddress); // 사용자가 작성한 게시물 목록을 조회하는 예시 코드

        for (Board board : boardList) {
            MyboardResDTO myboardResDTO = new MyboardResDTO();

            // 각 게시물에 필요한 정보를 설정합니다.
            myboardResDTO.setBoardId(board.getId().toString());
            myboardResDTO.setContractAddress(board.getContractAddress());
            myboardResDTO.setContent(board.getContent());
            myboardResDTO.setRegistrationTimeAgo(getRegistrationTimeAgo(board.getCreatedTime())); // 작성 시간을 계산하여 설정합니다            myboardResDTO.setLikeCount(board.getLikeCount());

            // 게시물에 달린 댓글 수를 조회합니다.
            int replyCount = replyRepository.countByBoardId(board.getId()); // 게시물에 달린 댓글 수를 조회하는 예시 코드
            myboardResDTO.setReplyCount(replyCount);

            // 좋아요 수를 조회합니다.
            int likeCount = likeRepository.countByBoardId(board.getId()); // 게시물의 좋아요 수를 조회하는 예시 코드
            myboardResDTO.setLikeCount(likeCount);

            myBoardList.add(myboardResDTO);
        }
        return myBoardList;
    }

    @Override
    public List<MyreplyResDTO> getMyReplyList(String userAddress) {
        List<MyreplyResDTO> myReplyList = new ArrayList<>();

        // userAddress를 기준으로 Reply 엔티티에서 해당 사용자의 댓글을 조회하는 로직을 구현합니다.
        List<Reply> replies = replyRepository.findByWriterAddress(userAddress);

        // Reply 엔티티를 MyreplyResDTO로 변환하여 myReplyList에 추가합니다.
        for (Reply reply : replies) {
            MyreplyResDTO myReply = new MyreplyResDTO();
            myReply.setReplyId(reply.getId());
            myReply.setBoardId(reply.getBoardId());
            myReply.setContent(reply.getContent());
            myReply.setRegistrationTimeAgo(getRegistrationTimeAgo(reply.getCreatedTime()));
            myReplyList.add(myReply);
        }

        return myReplyList;
    }
}
