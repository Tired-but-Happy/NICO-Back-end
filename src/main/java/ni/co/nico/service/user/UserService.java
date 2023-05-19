package ni.co.nico.service.user;

import ni.co.nico.domain.User;
import ni.co.nico.dto.user.UserUpdateReqDTO;

public interface UserService {
    public void login(String userAddress);
    public User getUserInfo(String userAddress);
    public void updateUserInfo(String userAddress, UserUpdateReqDTO updateReqDTO);
}
