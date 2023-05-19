package ni.co.nico.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class LikeBoard extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_id")
    private Long id;
    @Column
    private Long boardId;
    @Column
    private String userAddress;
}
