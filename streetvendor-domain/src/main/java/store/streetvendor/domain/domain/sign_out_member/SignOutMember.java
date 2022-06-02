package store.streetvendor.domain.domain.sign_out_member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;
import store.streetvendor.domain.domain.member.MemberProvider;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class SignOutMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberProvider provider;

    private String bossName;

    private String phoneNumber;

    public SignOutMember(Long id, Long memberId, String name, String nickName, String email, MemberProvider provider, String bossName, String phoneNumber) {
        this.id = id;
        this.memberId = memberId;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.provider = provider;
        this.bossName = bossName;
        this.phoneNumber = phoneNumber;
    }
}
