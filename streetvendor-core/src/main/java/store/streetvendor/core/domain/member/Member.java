package store.streetvendor.core.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.sign_out_member.SignOutMember;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    public Member(String name, String nickName, String email, String profileUrl, MemberProvider provider) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.profileUrl = profileUrl;
        this.provider = provider;
    }

    public static Member newGoogleInstance(String name, String nickName, String email, String profileUrl) {
        return new Member(name, nickName, email, profileUrl, MemberProvider.GOOGLE);
    }

    public static Member signOutMemberInstance(String name, String nickName, String email, String profileUrl) {
        return new Member(name, nickName, email, profileUrl, MemberProvider.GOOGLE);
    }

    public void changeProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    public SignOutMember signOut() {
        return SignOutMember.builder()
            .memberId(this.id)
            .provider(this.getProvider())
            .name(this.getName())
            .nickName(this.getNickName())
            .email(this.getEmail())
            .profileUrl(this.getProfileUrl())
            .build();
    }

}
