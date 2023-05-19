package ni.co.nico.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reply extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reply_id")
    private Long id;
    @Column
    private Long boardId;
    @Column
    private String content;
    @Column
    private String writerAddress;
    @Column
    private String writerNickName;
    @Column(columnDefinition = "integer default 0")
    private int likeCount;

    public LocalDateTime getCreatedTime() {
        return super.getCreatedAt();
    }
}
