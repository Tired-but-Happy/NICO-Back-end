package ni.co.nico.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class UsedApp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usedApp_id")
    private Long id;
    private String userAddress;
    private String appName;
    private String appCategory;

}
