package store.streetvendor.domain.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;
import store.streetvendor.domain.domain.sign_out_member.SignOutMember;

import javax.persistence.*;

import java.util.Objects;

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

    private String bossName;

    private String phoneNumber;

    @Builder
    private Member(String name, String nickName, String email, String profileUrl, MemberProvider provider, String bossName, String phoneNumber) {
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

    public Member findMember(Long memberId) {
        if (!Objects.equals(this.id, memberId))
            throw new IllegalArgumentException(String.format("찾으시는 <%s>는 존재하지 않습니다.", memberId));
        return this;
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
