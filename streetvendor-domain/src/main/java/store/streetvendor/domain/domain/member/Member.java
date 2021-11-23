package store.streetvendor.domain.domain.member;

import lombok.AccessLevel;
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

    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberProvider provider;

    private Member(String name, String nickName, String email, String profileUrl, MemberProvider provider) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.profileUrl = profileUrl;
        this.provider = provider;
    }

    public static Member newGoogleInstance(String name, String nickName, String email, String profileUrl) {
        return new Member(name, nickName, email, profileUrl, MemberProvider.GOOGLE);
    }

}
