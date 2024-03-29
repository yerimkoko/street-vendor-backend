package store.streetvendor.core.domain.sign_out_member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.member.Member;
import store.streetvendor.core.domain.member.MemberProvider;

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

    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberProvider provider;

    @Builder
    public SignOutMember(Long memberId, String name, String nickName, String email, String profileUrl, MemberProvider provider) {
        this.memberId = memberId;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.profileUrl = profileUrl;
        this.provider = provider;
    }

    public static SignOutMember of(Member member) {
        return new SignOutMember(member.getId(), member.getName(),
            member.getNickName(), member.getEmail(), member.getProfileUrl(), member.getProvider());
    }

}
