package ni.co.nico.dto.mypage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsedAppDTO {
    private String userAddress;
    private String appName;
    private int count;
    private String appCategory;
    private int year;
    private int quarter;
}
