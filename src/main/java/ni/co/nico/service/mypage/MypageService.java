package ni.co.nico.service.mypage;

import ni.co.nico.dto.mypage.MyboardResDTO;
import ni.co.nico.dto.mypage.MypageResDTO;
import ni.co.nico.dto.mypage.MyreplyResDTO;

import java.util.List;

public interface MypageService {
    public MypageResDTO getMyPageInfo(String userAddress);
    public List<MyboardResDTO> getMyBoardList(String userAddress);
    public List<MyreplyResDTO> getMyReplyList(String userAddress);
}
