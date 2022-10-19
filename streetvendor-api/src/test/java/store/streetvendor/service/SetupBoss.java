package store.streetvendor.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.streetvendor.domain.domain.member.Member;
import store.streetvendor.domain.domain.member.MemberProvider;
import store.streetvendor.domain.domain.member.MemberRepository;

import java.util.List;

@SpringBootTest
public abstract class SetupBoss {

    @Autowired
    public MemberRepository memberRepository;

    protected Member member = Member.builder()
        .name("뽀미")
        .nickName("뽀뽀")
        .email("gochi97@naver.com")
        .profileUrl("https://rabbit.com")
        .provider(MemberProvider.GOOGLE)
        .build();

    protected Member boss = Member.builder()
        .name("뽀미누나")
        .nickName("토끼")
        .email("gochi97@naver.com")
        .profileUrl("https://rabbit.com")
        .bossName("토끼쨩")
        .phoneNumber("010-2345-6789")
        .provider(MemberProvider.GOOGLE)
        .build();

    @BeforeEach
    void setupMember() {
        memberRepository.saveAll(List.of(member, boss));
    }

    protected void cleanup() {
        memberRepository.deleteAll();
    }

}
