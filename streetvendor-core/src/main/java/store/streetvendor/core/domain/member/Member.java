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

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String nickName;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 500)
    private String profileUrl;

    @Column(length = 50)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberProvider provider;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberType memberType;


    @Builder
    public Member(String name, String nickName, String email, String profileUrl, MemberProvider provider, MemberType memberType, String phoneNumber) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.profileUrl = profileUrl;
        this.provider = provider;
        this.memberType = memberType;
        this.phoneNumber = phoneNumber;
    }

    public static Member newGoogleUserInstance(String name, String nickName, String email, String profileUrl) {
        return new Member(name, nickName, email, profileUrl, MemberProvider.GOOGLE, MemberType.USER, null);
    }

    public static Member newGoogleBossInstance(String name, String nickName, String email, String profileUrl, String phoneNumber) {
        return new Member(name, nickName, email, profileUrl, MemberProvider.GOOGLE, MemberType.BOSS, phoneNumber);
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
