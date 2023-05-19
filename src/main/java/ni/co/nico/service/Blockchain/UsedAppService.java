package ni.co.nico.service.Blockchain;

import ni.co.nico.dto.mypage.UsedAppDTO;

import java.util.List;

public interface UsedAppService {
    public void processTransactionData(String json, String userAddress);
    public List<UsedAppDTO> getTopUsedAppsByUser(String userAddress);
}
