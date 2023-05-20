package ni.co.nico.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class User extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Column
    private String address;
    @Column(columnDefinition = "varchar(255)")
    private String nickName = "Supernico";
    @Column
    private String profileImage;
    @Column
    private String introduction;
    @Column(columnDefinition = "INT default 0")
    private int score;
    @Column(columnDefinition = "INT default 1")
    private int level;
    @Column
    private double balance;
    @Column(columnDefinition = "varchar(255) default 'Newbie'")
    private String style;
    @Column
    private String badge;

}
