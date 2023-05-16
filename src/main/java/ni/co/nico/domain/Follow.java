package ni.co.nico.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Follow extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "follow_id")
    private Long id;
    @Column
    private String followeeAddress;
    @Column
    private String followeeNickName;
    @Column
    private String followerAddress;
    @Column
    private String followerNickName;
}
