package ni.co.nico.service.follow.impl;

import lombok.RequiredArgsConstructor;
import ni.co.nico.domain.Follow;
import ni.co.nico.domain.User;
import ni.co.nico.repository.follow.FollowRepository;
import ni.co.nico.repository.user.UserRepository;
import ni.co.nico.service.follow.FollowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;



    public String followOnOff(String followeeAddress, String followerAddress) {
        Optional<Follow> followOptional = followRepository.findByFolloweeAddressAndFollowerAddress(followeeAddress, followerAddress);

        if (followOptional.isPresent()) {
            // 팔로우 기록이 이미 있는 경우
            Follow follow = followOptional.get();
            followRepository.delete(follow);
            return "unfollow";
        } else {
            // 팔로우 기록이 없는 경우
            Optional<User> followeeOptional = userRepository.findByAddress(followeeAddress);
            Optional<User> followerOptional = userRepository.findByAddress(followerAddress);

            if (followeeOptional.isPresent() && followerOptional.isPresent()) {
                User followee = followeeOptional.get();
                User follower = followerOptional.get();

                Follow follow = new Follow();
                follow.setFolloweeAddress(followee.getAddress());
                follow.setFolloweeNickName(followee.getNickName());
                follow.setFollowerAddress(follower.getAddress());
                follow.setFollowerNickName(follower.getNickName());

                followRepository.save(follow);
                return "follow";
            } else {
                throw new IllegalArgumentException("사용자를 찾을 수 없습니다."); // 예외 처리
                // 또는 적절한 로직 추가
            }
        }
    }
}
