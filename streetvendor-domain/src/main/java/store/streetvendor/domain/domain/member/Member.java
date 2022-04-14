package store.streetvendor.domain.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.domain.domain.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberProvider provider;

    private String bossName;

    private String phoneNumber;

    @Builder
    private Member(String name, String nickName, String email, String profileUrl, MemberProvider provider, MemberStatus status, String bossName, String phoneNumber) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.profileUrl = profileUrl;
        this.provider = provider;
        this.status = status;
        this.bossName = bossName;
        this.phoneNumber = phoneNumber;
    }

    public static Member newGoogleInstance(String name, String nickName, String email, String profileUrl) {
        return new Member(name, nickName, email, profileUrl, MemberProvider.GOOGLE, MemberStatus.ACTIVE, null, null);
    }

    public static Member bossInstance(String name, String nickName, String email, String profileUrl, String bossName, String phoneNumber) {
        return new Member(name, nickName, email, profileUrl, MemberProvider.GOOGLE, MemberStatus.ACTIVE, bossName, phoneNumber);
    }

    public void changeStatus() {
        this.status = MemberStatus.SIGN_OUT;
    }

    public void setBossNameAndNumber(String bossName, String phoneNumber) {
        this.bossName = bossName;
        this.phoneNumber = phoneNumber;
    }

}
