package ni.co.nico.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class LikeReply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_reply_id")
    private Long id;
    @Column
    private Long replyId;
    @Column
    private String userAddress;
}
