package store.streetvendor.service.store;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberProvider;
import store.streetvendor.domain.domain.member.MemberRepository;

import java.util.List;

@SpringBootTest
public abstract class SetupMember {

    @Autowired
    protected MemberRepository memberRepository;

    protected Member member = Member.builder()
        .name("yerimkoko")
        .nickName("yerimko")
        .email("gochi97@naver.com")
        .profileUrl("https://rabbit.com")
        .provider(MemberProvider.GOOGLE)
        .build();

    protected Member boss = Member.builder()
        .name("yerimkoko")
        .nickName("yerimko")
        .email("gochi97@naver.com")
        .profileUrl("https://rabbit.com")
        .bossName("고토끼")
        .phoneNumber("010-2345-6789")
        .provider(MemberProvider.GOOGLE)
        .build();


    @BeforeEach
    void setupMember() {
        memberRepository.saveAll(List.of(member, member));
    }

    @AfterEach
    void cleanup() {
        memberRepository.deleteAllInBatch();
    }

}
