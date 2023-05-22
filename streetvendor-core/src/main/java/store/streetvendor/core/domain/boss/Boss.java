package store.streetvendor.core.domain.boss;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.streetvendor.core.domain.BaseTimeEntity;
import store.streetvendor.core.domain.member.MemberProvider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class Boss extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String nickName;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100)
    private String accountNumber;

    @Column(length = 200, nullable = false)
    private String email;

    @Column(length = 500, nullable = false)
    private String profileUrl;

    @Column(length = 200, nullable = false)
    private MemberProvider provider;

    @Column(length = 50, nullable = false)
    private String phoneNumber;

    @Builder
    public Boss(String nickName, String name, String accountNumber, String email, String profileUrl, MemberProvider provider, String phoneNumber) {
        this.nickName = nickName;
        this.name = name;
        this.accountNumber = accountNumber;
        this.email = email;
        this.profileUrl = profileUrl;
        this.provider = provider;
        this.phoneNumber = phoneNumber;
    }

    public static Boss newGoogleInstance(String nickName, String name, String accountNumber, String email, String profileUrl, String phoneNumber) {
        return new Boss(nickName, name, accountNumber, email, profileUrl, MemberProvider.GOOGLE, phoneNumber);
    }
}
