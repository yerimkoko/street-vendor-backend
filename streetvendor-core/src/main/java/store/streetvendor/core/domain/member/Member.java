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

    @Column
    private String bossName;

    @Column
    private String phoneNumber;

    @Builder
    public Member(String name, String nickName, String email, String profileUrl, MemberProvider provider, String bossName, String phoneNumber) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.profileUrl = profileUrl;
        this.provider = provider;
        this.bossName = bossName;
        this.phoneNumber = phoneNumber;
    }

    public static Member newGoogleInstance(String name, String nickName, String email, String profileUrl) {
        return new Member(name, nickName, email, profileUrl, MemberProvider.GOOGLE, null, null);
    }

    public static Member bossInstance(String name, String nickName, String email, String profileUrl, String bossName, String phoneNumber) {
        return new Member(name, nickName, email, profileUrl, MemberProvider.GOOGLE, bossName, phoneNumber);
    }

    public static Member signOutMemberInstance(String name, String nickName, String email, String profileUrl) {
        return new Member(name, nickName, email, profileUrl, MemberProvider.GOOGLE, null, null);
    }

    public void setBossNameAndNumber(String bossName, String phoneNumber) {
        this.bossName = bossName;
        this.phoneNumber = phoneNumber;
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
            .bossName(this.getBossName())
            .phoneNumber(this.getPhoneNumber())
            .profileUrl(this.getProfileUrl())
            .build();
    }

}
