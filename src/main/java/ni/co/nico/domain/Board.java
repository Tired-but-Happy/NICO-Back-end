package ni.co.nico.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Board extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;
    @Column
    private String contractAddress;
    @Column
    private String writerAddress;
    @Column
    private String writerNickName;
    @Column
    private String content;
    @Column(columnDefinition = "integer default 0")
    private int likeCount;
    @Column(columnDefinition = "boolean default false")
    private boolean writerScoreReceived;

    // 작성 시간을 반환하는 getter 메서드
    public LocalDateTime getCreatedTime() {
        return super.getCreatedAt();
    }

    public boolean isWriterScoreReceived() {
        return writerScoreReceived;
    }
}
