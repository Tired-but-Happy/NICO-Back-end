package ni.co.nico.service.style;

import java.util.List;

public interface StyleService {
    public void getNewbieStyle(String userAddress);
    public List<String> getStyleList(String ownerAddress);
}
